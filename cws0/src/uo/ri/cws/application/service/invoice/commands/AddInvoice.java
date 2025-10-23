package uo.ri.cws.application.service.invoice.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceRecordAssembler;
import uo.ri.cws.application.persistence.invoice.InvoiceWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceWorkOrderGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddInvoice implements Command<InvoiceDto> {

    private final List<String> workOrderIds;
    private final InvoiceDto dto = new InvoiceDto();
    private final InvoiceWorkOrderGateway ig = Factories.persistence.forInvoice();
    private final WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    public AddInvoice(List<String> workOrderIds) {
        ArgumentChecks.isNotNull(workOrderIds);
        ArgumentChecks.isTrue(workOrderIds.size() > 0);
        for(String s : workOrderIds) {
            ArgumentChecks.isNotNull(s);
            ArgumentChecks.isNotBlank(s);
            ArgumentChecks.isNotEmpty(s);
        }
        // Initialize DTO fields
        dto.id = UUID.randomUUID().toString();
        dto.state = "NOT_YET_PAID";
        dto.version = 1L;
        this.workOrderIds = workOrderIds;
    }

    public InvoiceDto execute() throws BusinessException {
        // 1. Validate all work orders exist and are FINISHED
        List<WorkOrderRecord> workOrders = new ArrayList<>();
        double totalAmount = 0.0;

        for(String id : workOrderIds) {
            Optional<WorkOrderRecord> wo = wg.findById(id);
            BusinessChecks.exists(wo, "The WorkOrder does not exist");

            WorkOrderRecord record = wo.get();
            
            if(!"FINISHED".equalsIgnoreCase(record.state)) {
	            throw new BusinessException("WorkOrder " + id + " is not in FINISHED state");
            }
			
            totalAmount += record.amount;
            workOrders.add(record);
        }

        // 2. Calculate invoice totals
        dto.date = LocalDate.now();
        double vatRate = vatPercentage(dto.date);

        // Amount WITHOUT VAT
        double amountWithoutVat = totalAmount;
        // VAT amount
        dto.vat = Rounds.toCents(amountWithoutVat * (vatRate / 100.0));
        // Total amount WITH VAT
        dto.amount = Rounds.toCents(amountWithoutVat + dto.vat);
		
		long maxNumber = ig.findMaxInvoiceNumber();
		dto.number = maxNumber + 1;
		
        // 3. Convert DTO to Record and persist
        InvoiceRecord ir = InvoiceRecordAssembler.toRecord(dto);
        ig.add(ir);

        // Update DTO with generated number
        dto.number = ir.number;

        // 4. Update all work orders with invoice reference
        for(WorkOrderRecord r : workOrders) {
            r.invoiceId = dto.id;
            r.state = "INVOICED";
            r.updatedAt = LocalDateTime.now();
            wg.update(r);
        }

        return dto;
			
    }

    private double vatPercentage(LocalDate d) {
        return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;
    }
}

package uo.ri.cws.application.service.invoice.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceWorkOrderGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

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
		dto.id = UUID.randomUUID().toString();
		dto.state = "NOT_YET_PAID";
		dto.version = 1L;
		this.workOrderIds = workOrderIds;
	}
	
	public InvoiceDto execute() throws BusinessException {

        InvoiceDto idto = new InvoiceDto();

        try {
            InvoiceRecord ir = InvoiceRecordAssembler.toRecord(dto);
            List<WorkOrderRecord> wr = new ArrayList<>();
            double amount = 0;
            for(String id : workOrderIds){
                Optional<WorkOrderRecord> wo = wg.findById(id);
                BusinessChecks.exists(wo, "The WorkOrder does not exist");
                WorkOrderRecord r = wo.get();
                amount += r.amount;
                wr.add(r);
            }

            ir.date = LocalDate.now();
            // included
            double vat = vatPercentage(ir.date);
            ir.vat = ir.amount * ( vat / 100); // vat amount
            double total = ir.amount * ir.vat; // vat included
            ir.amount = Rounds.toCents(total);

            ig.add(ir);

            for(WorkOrderRecord r : wr){
                r.invoiceId = ir.id;
                r.updatedAt = LocalDateTime.now();
                wg.update(r);
            }

        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }

        return idto;
		
	}

    private double vatPercentage(LocalDate d) {
        return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;

    }

}

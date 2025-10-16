package uo.ri.cws.application.persistence.invoice;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.persistence.invoice.InvoiceWorkOrderGateway.InvoiceRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class InvoiceRecordAssembler {

    public static InvoiceRecord toRecord(InvoiceDto dto) {
        InvoiceRecord ir = new InvoiceRecord();
        ir.id = dto.id;
        ir.number = dto.number;
        ir.date = dto.date;
        ir.vat = dto.vat;
        ir.amount = dto.amount;
        ir.state = dto.state;
        ir.version = dto.version;
        ir.createdAt = LocalDateTime.now();
        ir.updatedAt = LocalDateTime.now();
        ir.entityState = "ENABLED";
        return ir;
    }

    public static InvoiceDto toDto(InvoiceRecord ir) {
        InvoiceDto idto = new InvoiceDto();
        idto.id = ir.id;
        idto.number = ir.number;
        idto.date = ir.date;
        idto.vat = ir.vat;
        idto.amount = ir.amount;
        idto.state = ir.state;
        idto.version = ir.version;
        return idto;
    }

    public static InvoiceWorkOrderGateway.InvoicingWorkOrderRecord toRecord(ResultSet rs) throws SQLException {
        InvoiceWorkOrderGateway.InvoicingWorkOrderRecord ir = new InvoiceWorkOrderGateway.InvoicingWorkOrderRecord();
        ir.id = rs.getString(1);
        ir.description = rs.getString(2);
        ir.date = rs.getTimestamp(3).toLocalDateTime();
        ir.state = rs.getString(4);
        ir.amount = rs.getDouble(5);
        return ir;
    }

    public static InvoicingService.InvoicingWorkOrderDto toDto(InvoiceWorkOrderGateway.InvoicingWorkOrderRecord ir) {
        InvoicingService.InvoicingWorkOrderDto idto = new InvoicingService.InvoicingWorkOrderDto();
        idto.id = ir.id;
        idto.description = ir.description;
        idto.date = ir.date;
        idto.state = ir.state;
        idto.amount = ir.amount;
        return idto;
    }

}

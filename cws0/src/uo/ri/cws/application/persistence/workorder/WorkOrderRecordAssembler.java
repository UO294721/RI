package uo.ri.cws.application.persistence.workorder;

import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class WorkOrderRecordAssembler {

    public static WorkOrderRecord toRecord(WorkOrderDto dto) {

        WorkOrderRecord wr = new WorkOrderRecord();
        wr.id = dto.id;
        wr.description = dto.description;
        wr.date = dto.date;
        wr.state = dto.state;
        wr.amount = dto.amount;
        wr.version = dto.version;
        wr.entityState = "ENABLED";
        wr.invoiceId = dto.invoiceId;
        wr.mechanicId = dto.mechanicId;
        wr.vehicleId = dto.vehicleId;
        return wr;

    }

    public static WorkOrderRecord toRecord(ResultSet rs) throws SQLException {

        WorkOrderRecord wr = new WorkOrderRecord();
        wr.id = rs.getString("id");
        wr.description = rs.getString("description");
        wr.date = rs.getTimestamp("date").toLocalDateTime();
        wr.state = rs.getString("state");
        wr.amount = rs.getDouble("amount");
        wr.version = rs.getLong("version");
        wr.invoiceId = rs.getString("invoice_id");
        wr.mechanicId = rs.getString("mechanic_id");
        wr.vehicleId = rs.getString("vehicle_id");
        return wr;

    }

}

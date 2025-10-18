package uo.ri.cws.application.persistence.workorder;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;

import java.time.LocalDateTime;

public interface WorkOrderGateway extends Gateway<WorkOrderGateway.WorkOrderRecord> {

    class WorkOrderRecord {
        public String id;
        public long version;

        public String vehicleId;
        public String description;
        public LocalDateTime date;
        public double amount;
        public String state;

        // might be null
        public String mechanicId;
        public String invoiceId;

        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;
    }

    boolean hasActiveWorkOrders(String mechanicId) throws PersistenceException;

}

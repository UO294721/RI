package uo.ri.cws.application.persistence.contract;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ContractGateway extends Gateway<ContractRecord> {

    public class ContractRecord {
        public String id;
        public long version;

        public String mechanicId;
        public String contractTypeId;
        public String professionalGroupId;
        public LocalDate startDate;
        public LocalDate endDate;
        public double annualBaseSalary;
        public double settlement;
        public String state;

        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;
    }

    /**
     * Checks if a mechanic has a contract in force
     */
    boolean hasInForceContract(String mechanicId) throws PersistenceException;

    /**
     * Checks if a mechanic has a terminated contract
     */
    boolean hasTerminatedContract(String mechanicId) throws PersistenceException;

    // ... other methods
}

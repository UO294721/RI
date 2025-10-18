package uo.ri.cws.application.persistence.contract;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContractGateway extends Gateway<ContractGateway.ContractRecord> {
	
	public class ContractRecord {
		public String id;
		public long version;
		
		public String mechanicId;
		public String contractTypeId;
		public String professionalGroupId;
		
		public LocalDate startDate;
		public LocalDate endDate;
		public double annualBaseSalary;
		public double taxRate;
		public double settlement;
		public String state;
		
		public LocalDateTime createdAt;
		public LocalDateTime updatedAt;
		public String entityState;
	}
	
	/**
	 * Finds the active contract for a mechanic
	 */
	Optional<ContractRecord> findInforceByMechanicId(String mechanicId) throws PersistenceException;
	
	/**
	 * Finds all contracts for a mechanic
	 */
	List<ContractRecord> findByMechanicId(String mechanicId) throws PersistenceException;
	
	/**
	 * Finds all contracts in force
	 */
	List<ContractRecord> findInforceContracts() throws PersistenceException;
	
	Optional<ContractRecord> findByType(String name) throws PersistenceException;
	
	/**
	 * Counts payrolls for a contract
	 */
	int countPayrollsByContractId(String contractId) throws PersistenceException;
}

package uo.ri.cws.application.persistence.contract;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Gateway for Contract persistence operations.
 * Follows the Table Data Gateway pattern.
 */
public interface ContractGateway extends Gateway<ContractRecord> {
	
	/**
	 * Record representing a Contract in the database
	 */
	class ContractRecord {
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
	 * Finds the active (in force) contract for a mechanic
	 * @param mechanicId the mechanic's ID
	 * @return Optional containing the contract if found
	 * @throws PersistenceException on database errors
	 */
	Optional<ContractRecord> findInforceByMechanicId(String mechanicId)
			throws PersistenceException;
	
	/**
	 * Finds all contracts (any state) for a mechanic
	 * @param mechanicId the mechanic's ID
	 * @return List of contracts, may be empty
	 * @throws PersistenceException on database errors
	 */
	List<ContractRecord> findByMechanicId(String mechanicId)
			throws PersistenceException;
	
	/**
	 * Finds all contracts that are currently in force
	 * @return List of in-force contracts, may be empty
	 * @throws PersistenceException on database errors
	 */
	List<ContractRecord> findInforceContracts()
			throws PersistenceException;
	
	/**
	 * Finds contracts by contract type ID
	 * @param contractTypeId the contract type ID
	 * @return Optional containing the first contract if found
	 * @throws PersistenceException on database errors
	 */
	Optional<ContractRecord> findByContractTypeId(String contractTypeId)
			throws PersistenceException;
	
	/**
	 * Counts the number of payrolls associated with a contract
	 * @param contractId the contract ID
	 * @return number of payrolls
	 * @throws PersistenceException on database errors
	 */
	int countPayrollsByContractId(String contractId)
			throws PersistenceException;
}
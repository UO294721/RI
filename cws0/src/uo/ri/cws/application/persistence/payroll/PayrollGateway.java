package uo.ri.cws.application.persistence.payroll;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Gateway for Payroll persistence operations.
 * Follows the Table Data Gateway pattern.
 */
public interface PayrollGateway extends Gateway<PayrollGateway.PayrollRecord> {
	
	/**
	 * Record representing a Payroll in the database
	 */
	class PayrollRecord {
		public String id;
		public long version;
		
		public String contractId;
		public LocalDate date;
		
		// Earnings
		public double baseSalary;
		public double extraSalary;
		public double productivityEarning;
		public double trienniumEarning;
		
		// Deductions
		public double taxDeduction;
		public double nicDeduction;
		
		public LocalDateTime createdAt;
		public LocalDateTime updatedAt;
		public String entityState;
	}
	
	/**
	 * Finds the last 12 payrolls for a given contract, ordered by date descending
	 * @param contractId the contract's ID
	 * @return List of up to 12 payroll records, may be less if contract is new
	 * @throws PersistenceException on database errors
	 */
	List<PayrollRecord> findLast12ByContractId(String contractId)
			throws PersistenceException;
	
	/**
	 * Finds all payrolls for a specific contract
	 * @param contractId the contract's ID
	 * @return List of payrolls, may be empty
	 * @throws PersistenceException on database errors
	 */
	List<PayrollRecord> findByContractId(String contractId)
			throws PersistenceException;
	
	/**
	 * Finds payrolls by date
	 * @param date the payroll date
	 * @return List of payrolls for that date, may be empty
	 * @throws PersistenceException on database errors
	 */
	List<PayrollRecord> findByDate(LocalDate date)
			throws PersistenceException;
	
	/**
	 * Finds the payroll for a specific contract and date
	 * @param contractId the contract's ID
	 * @param date the payroll date
	 * @return List containing the payroll if exists, empty otherwise
	 * @throws PersistenceException on database errors
	 */
	List<PayrollRecord> findByContractIdAndDate(String contractId, LocalDate date)
			throws PersistenceException;
}
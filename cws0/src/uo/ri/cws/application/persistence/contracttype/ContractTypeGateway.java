package uo.ri.cws.application.persistence.contracttype;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ContractTypeGateway extends Gateway<ContractTypeRecord> {
	
	public class ContractTypeRecord {
		public String id;
		public long version;
		
		public String name;
		public double compensationDays;
		
		public LocalDateTime createdAt;
		public LocalDateTime updatedAt;
		public String entityState;
	}
	
	/**
	 * Finds a contract type by name
	 * @param name the contract type name
	 * @return Optional containing the contract typeif found
	 * @throws PersistenceException on database errors
	 */
	Optional<ContractTypeRecord> findByName(String name) throws PersistenceException;
}
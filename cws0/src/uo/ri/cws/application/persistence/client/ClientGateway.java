package uo.ri.cws.application.persistence.client;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Gateway for Client persistence operations.
 * Follows the Table Data Gateway pattern.
 */
public interface ClientGateway extends Gateway<ClientRecord> {
	
	/**
	 * Record representing a Client in the database
	 */
	class ClientRecord {
		public String id;
		public long version;
		
		public String nif;
		public String name;
		public String surname;
		public String addressStreet;
		public String addressCity;
		public String addressZipcode;
		public String phone;
		public String email;
		
		public LocalDateTime createdAt;
		public LocalDateTime updatedAt;
		public String entityState;
	}
	
	/**
	 * Finds a client by NIF
	 * @param nif the client's NIF
	 * @return Optional containing the client if found
	 * @throws PersistenceException on database errors
	 */
	Optional<ClientRecord> findByNif(String nif) throws PersistenceException;
	
}
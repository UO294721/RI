package uo.ri.cws.application.persistence.professionalgroup;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProfessionalGroupGateway extends Gateway<ProfessionalGroupRecord> {
	
	public class ProfessionalGroupRecord {
		public String id;
		public long version;
		
		public String name;
		public double trienniumPayment;
		public double productivityRate;
		
		public LocalDateTime createdAt;
		public LocalDateTime updatedAt;
		public String entityState;
	}
	
	/**
	 * Finds a professional group by name
	 */
	Optional<ProfessionalGroupRecord> findByName(String name) throws PersistenceException;
}
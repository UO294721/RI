package uo.ri.cws.application.persistence.mechanic;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord> {
	
	class MechanicRecord{
		public String id;
		public long version;
		
		public String nif;
		public String name;
		public String surname;
		
		public LocalDateTime createdAt;
		public LocalDateTime updatedAt;
		public String entityState;
	}
	
	/**
	 * 
	 * @param nif
	 * @return
	 */
	Optional<MechanicRecord> findByNif(String nif);

}

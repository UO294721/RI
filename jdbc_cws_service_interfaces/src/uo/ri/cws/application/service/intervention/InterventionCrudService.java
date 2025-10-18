package uo.ri.cws.application.service.intervention;

import java.time.LocalDateTime;

/**
 * This service is intended to be used by the Mechanic
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface InterventionCrudService {

	// ...

	
	class InterventionDto {
		public String id;
		public long version;

		public int minutes;
		public LocalDateTime date;
		public String mechanicId;
		public String workOrderId;

	}

}

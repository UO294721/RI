package uo.ri.cws.application.service.workorder;

import uo.ri.util.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This service is intended to be used by the Mechanic
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface WorkOrderCrudService {

    Optional<WorkOrderDto> findById(String id) throws BusinessException;
    void update(WorkOrderDto dto) throws BusinessException;

	public static class WorkOrderDto {
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
	}
}

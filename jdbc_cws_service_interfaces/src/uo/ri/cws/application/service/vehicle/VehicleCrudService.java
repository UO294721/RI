package uo.ri.cws.application.service.vehicle;

import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.Optional;

/**
 * This service is intended to be used by the Foreman
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface VehicleCrudService {

	/**
	 * @param plate number
	 * @return an Optional with the vehicle dto specified be the plate number
	 *
	 * @throws BusinessException, DOES NOT
	 * @throws IllegalArgumentException if the plate is null
	 */
	Optional<VehicleDto> findByPlate(String plate) throws BusinessException;
	
	VehicleDto create(VehicleDto dto) throws BusinessException;
	
	void update(VehicleDto dto) throws BusinessException;
	
	void delete(String id) throws BusinessException;
	
	List<VehicleDto> findAll() throws BusinessException;

	class VehicleDto {
		public String id;
		public long version;

		public String plate;
		public String make;
		public String model;

		public String clientId;
		public String vehicleTypeId;

	}

}

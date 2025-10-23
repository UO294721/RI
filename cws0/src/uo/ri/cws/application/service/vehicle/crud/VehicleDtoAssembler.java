package uo.ri.cws.application.service.vehicle.crud;

import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;

public class VehicleDtoAssembler {
	
	public static VehicleDto toDto(VehicleRecord vr) {
		VehicleDto dto = new VehicleDto();
		dto.clientId = vr.clientId;
		dto.id = vr.id;
		dto.make = vr.make;
		dto.model = vr.model;
		dto.plate = vr.plate;
		dto.vehicleTypeId = vr.vehicleTypeId;
		dto.version = vr.version;
		return dto;
	}
	
}

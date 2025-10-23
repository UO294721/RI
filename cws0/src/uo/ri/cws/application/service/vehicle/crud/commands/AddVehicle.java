package uo.ri.cws.application.service.vehicle.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleRecordAssembler;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class AddVehicle implements Command<VehicleDto> {
	
	private final VehicleGateway vg = Factories.persistence.forVehicle();
	private VehicleDto dto;
	
	public AddVehicle( VehicleDto dto ) {
		ArgumentChecks.isNotNull(dto, "Vehicle dto cannot be null");
		ArgumentChecks.isNotEmpty(dto.id);
		ArgumentChecks.isNotEmpty(dto.vehicleTypeId);
		ArgumentChecks.isNotEmpty(dto.plate);
		ArgumentChecks.isNotEmpty(dto.make);
		ArgumentChecks.isNotEmpty(dto.model);
		ArgumentChecks.isNotNull(dto.clientId);
		this.dto = dto;
	}
	
	@Override
	public VehicleDto execute() throws BusinessException {
		
		try{
			vg.add(VehicleRecordAssembler.toRecord(dto));
			return dto;
		} catch(PersistenceException e) {
			throw new BusinessException("Error adding the vehicle");
		}
		
	}
}

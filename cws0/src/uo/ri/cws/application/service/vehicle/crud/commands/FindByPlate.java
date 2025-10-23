package uo.ri.cws.application.service.vehicle.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.vehicle.crud.VehicleDtoAssembler;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class FindByPlate implements Command<Optional<VehicleDto>> {
	
	private String plate;
	private VehicleGateway vg = Factories.persistence.forVehicle();
	
	public FindByPlate(String plate) {
		this.plate = plate;
	}
	
	@Override
	public Optional<VehicleDto> execute() throws BusinessException {
		
		Optional<VehicleRecord> ovr = Optional.empty();
		VehicleRecord vr = null;
		VehicleDto dto = null;
		
		try{
			ovr = vg.findByPlate(plate);
			if(ovr.isEmpty()) return Optional.empty();
			vr = ovr.orElse(null);
			dto = VehicleDtoAssembler.toDto(vr);
		} catch(PersistenceException e) {
			throw new BusinessException("Error reading the vehicle");
		}
		
		return Optional.of(dto);
	}
}

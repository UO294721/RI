package uo.ri.cws.application.service.vehicle.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.vehicle.crud.VehicleDtoAssembler;
import uo.ri.util.exception.BusinessException;

import java.util.List;

public class ListAllVehicles implements Command<List<VehicleDto>> {
	
	private final VehicleGateway vg = Factories.persistence.forVehicle();
	
	@Override
	public List<VehicleDto> execute() throws BusinessException {
		
		try{
			return vg.findAll().stream().map(VehicleDtoAssembler::toDto).toList();
		} catch (PersistenceException e) {
			throw new BusinessException(e);
		}
	}
}

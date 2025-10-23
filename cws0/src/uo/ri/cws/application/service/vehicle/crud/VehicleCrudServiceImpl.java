package uo.ri.cws.application.service.vehicle.crud;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.commands.*;
import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public class VehicleCrudServiceImpl implements VehicleCrudService {
	
	CommandExecutor executor = new CommandExecutor();
	
	@Override
	public Optional<VehicleDto> findByPlate(String plate)
			throws BusinessException {
		return executor.execute(new FindByPlate(plate));
	}
	
	@Override
	public VehicleDto create(VehicleDto dto) throws BusinessException {
		return executor.execute(new AddVehicle(dto));
	}
	
	@Override
	public void update(VehicleDto dto) throws BusinessException {
		executor.execute(new UpdateVehicle(dto));
	}
	
	@Override
	public void delete(String id) throws BusinessException {
		executor.execute(new DeleteVehicle(id));
	}
	
	@Override
	public List<VehicleDto> findAll() throws BusinessException {
		return executor.execute(new ListAllVehicles());
	}
}

package uo.ri.cws.application.service.vehicle.crud;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.commands.FindByPlate;
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
		return null;
	}
	
	@Override
	public void update(VehicleDto dto) throws BusinessException {
	
	}
	
	@Override
	public void delete(String id) throws BusinessException {
	
	}
	
	@Override
	public List<VehicleDto> findAll() throws BusinessException {
		return List.of();
	}
}

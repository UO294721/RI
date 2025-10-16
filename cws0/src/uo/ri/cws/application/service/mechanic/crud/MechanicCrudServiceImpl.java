package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.ListAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanicById;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanicByNif;
import uo.ri.cws.application.service.mechanic.crud.commands.UpdateMechanic;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {
	
	CommandExecutor executor = new CommandExecutor();
	
	@Override
	public MechanicDto create(MechanicDto dto) throws BusinessException{
		return executor.execute(new AddMechanic(dto));
	}
	
	@Override
	public void delete( String id ) throws BusinessException {
		executor.execute(new DeleteMechanic(id));
	}

	@Override
	public void update(MechanicDto dto) throws BusinessException {
		executor.execute(new UpdateMechanic(dto));
	}

	@Override
	public Optional<MechanicDto> findById(String id) throws BusinessException {
		return executor.execute(new ListMechanicById(id));
	}

	@Override
	public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
		return executor.execute(new ListMechanicByNif(nif));
	}

	@Override
	public List<MechanicDto> findAll() throws BusinessException {
		return executor.execute(new ListAllMechanics());
	}

}

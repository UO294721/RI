package uo.ri.cws.application.service.client;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.client.commands.*;
import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public class ClientCrudServiceImpl implements ClientCrudService {
	
	private final CommandExecutor executor = new CommandExecutor();
	
	@Override
	public ClientDto create(ClientDto dto) throws BusinessException {
		return executor.execute(new AddClient(dto));
	}
	
	@Override
	public Optional<ClientDto> findById(String id) throws BusinessException {
		return executor.execute(new FindClientById(id));
	}
	
	@Override
	public Optional<ClientDto> findByNif(String nif) throws BusinessException {
		return executor.execute(new FindClientByNif(nif));
	}
	
	@Override
	public void update(ClientDto dto) throws BusinessException {
		executor.execute(new UpdateClient(dto));
	}
	
	@Override
	public void delete(String id) throws BusinessException {
		executor.execute(new DeleteClient(id));
	}
	
	@Override
	public List<ClientDto> findAll() throws BusinessException {
		return executor.execute(new FindAllClients());
	}
}
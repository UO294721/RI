package uo.ri.cws.application.service.client;

import java.util.List;
import java.util.Optional;

public class ClientCrudServiceImpl implements ClientCrudService{
	@Override
	public ClientDto create(ClientDto dto) {
		return null;
	}
	
	@Override
	public Optional<ClientDto> findById(String id) {
		return Optional.empty();
	}
	
	@Override
	public void update(ClientDto dto) {
	
	}
	
	@Override
	public void delete(String id) {
	
	}
	
	@Override
	public List<ClientDto> findAll() {
		return List.of();
	}
}

package uo.ri.cws.application.service.client.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.persistence.client.ClientRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;
import java.util.UUID;

public class AddClient implements Command<ClientDto> {
	
	private final ClientDto dto;
	private final ClientGateway cg = Factories.persistence.forClient();
	
	public AddClient(ClientDto dto) {
		ArgumentChecks.isNotNull(dto, "Client dto cannot be null");
		ArgumentChecks.isNotBlank(dto.nif, "NIF cannot be blank");
		ArgumentChecks.isNotBlank(dto.name, "Name cannot be blank");
		ArgumentChecks.isNotBlank(dto.surname, "Surname cannot be blank");
		
		// Initialize DTO fields
		dto.id = UUID.randomUUID().toString();
		dto.version = 1L;
		this.dto = dto;
	}
	
	@Override
	public ClientDto execute() throws BusinessException {
		try {
			// Check if client with same NIF already exists
			Optional<ClientRecord> existingClient = cg.findByNif(dto.nif);
			if (existingClient.isPresent()) {
				throw new BusinessException("A client with NIF '" + dto.nif + "' already exists");
			}
			
			ClientRecord cr = ClientRecordAssembler.toRecord(dto);
			
			cg.add(cr);
			
			return dto;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error creating client", e);
		}
	}
}
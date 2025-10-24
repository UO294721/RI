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

public class FindClientById implements Command<Optional<ClientDto>> {
	
	private final String id;
	private final ClientGateway cg = Factories.persistence.forClient();
	
	public FindClientById(String id) {
		ArgumentChecks.isNotBlank(id, "Client id cannot be blank");
		this.id = id;
	}
	
	@Override
	public Optional<ClientDto> execute() throws BusinessException {
		try {
			Optional<ClientRecord> ocr = cg.findById(id);
			
			if (ocr.isEmpty()) {
				return Optional.empty();
			}
			
			ClientRecord cr = ocr.get();
			ClientDto dto = ClientRecordAssembler.toDto(cr);
			
			return Optional.of(dto);
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error finding client by id", e);
		}
	}
}
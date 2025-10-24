package uo.ri.cws.application.service.client.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.persistence.client.ClientRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class FindClientByNif implements Command<Optional <ClientDto>> {
	
	private String nif;
	private final ClientGateway cg = Factories.persistence.forClient();
	
	public FindClientByNif(String nif) {
		this.nif = nif;
	}
	
	@Override
	public Optional<ClientDto> execute() throws BusinessException {
		
		try{
			Optional<ClientRecord> ocr = cg.findByNif(nif);
			
			if(ocr.isEmpty()) return Optional.empty();
			
			ClientRecord cr = ocr.get();
			
			return Optional.of(ClientRecordAssembler.toDto(cr));
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error finding client by nif", e);
		}
		
	}
}

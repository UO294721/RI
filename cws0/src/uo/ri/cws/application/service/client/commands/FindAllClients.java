package uo.ri.cws.application.service.client.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.persistence.client.ClientRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.util.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class FindAllClients implements Command<List<ClientDto>> {
	
	private final ClientGateway cg = Factories.persistence.forClient();
	
	@Override
	public List<ClientDto> execute() throws BusinessException {
		try {
			List<ClientRecord> clients = cg.findAll();
			List<ClientDto> result = new ArrayList<>();
			
			for (ClientRecord cr : clients) {
				result.add(ClientRecordAssembler.toDto(cr));
			}
			
			return result;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error finding all clients", e);
		}
	}
}
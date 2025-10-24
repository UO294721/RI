package uo.ri.cws.application.service.client.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class UpdateClient implements Command<Void> {
	
	private final ClientDto dto;
	private final ClientGateway cg = Factories.persistence.forClient();
	
	public UpdateClient(ClientDto dto) {
		ArgumentChecks.isNotNull(dto, "Client dto cannot be null");
		ArgumentChecks.isNotBlank(dto.id, "Client id cannot be blank");
		ArgumentChecks.isNotBlank(dto.name, "Name cannot be blank");
		ArgumentChecks.isNotBlank(dto.surname, "Surname cannot be blank");
		
		this.dto = dto;
	}
	
	@Override
	public Void execute() throws BusinessException {
		try {
			// Find client
			Optional<ClientRecord> ocr = cg.findById(dto.id);
			BusinessChecks.exists(ocr, "Client does not exist");
			
			ClientRecord cr = ocr.get();
			
			// Check version
			BusinessChecks.hasVersion(dto.version, cr.version);
			
			// Update fields
			cr.name = dto.name;
			cr.surname = dto.surname;
			cr.addressStreet = dto.addressStreet;
			cr.addressCity = dto.addressCity;
			cr.addressZipcode = dto.addressZipcode;
			cr.phone = dto.phone;
			cr.email = dto.email;
			// Persist
			cg.update(cr);
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error updating client", e);
		}
	}
}
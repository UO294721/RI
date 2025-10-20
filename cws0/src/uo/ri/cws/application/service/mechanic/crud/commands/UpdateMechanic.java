package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class UpdateMechanic implements Command<Void>{
	
	private final MechanicDto dto;
	private final MechanicGateway mg = Factories.persistence.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}
	
	public Void execute() throws BusinessException {
		try {
			checkFields(dto);
			
			Optional<MechanicRecord> om = mg.findById(dto.id);
			BusinessChecks.exists(om, "The mechanic does not exist");
			MechanicRecord r = om.get();
			BusinessChecks.hasVersion(dto.version, r.version);
			
			if(mg.findByNif(dto.nif).isPresent())
				throw new BusinessException("The mechanic already exists");
			
			mg.update(MechanicDtoAssembler.toRecord(dto));
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error updating mechanic", e);
		}
	}
	
	private void checkFields(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		checkField(dto.name);
		checkField(dto.surname);
		checkField(dto.nif);
		checkField(dto.id);
	}
	
	private void checkField(String field) {
        ArgumentChecks.isNotNull(field);
		ArgumentChecks.isNotBlank(field);
		ArgumentChecks.isNotEmpty(field);
	}

}

package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.Assert;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void>{
	
	private MechanicDto dto;
	private MechanicGateway mg = Factories.persistence.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}
	
	public Void execute() throws BusinessException {

        checkFields(dto);

		Optional<MechanicRecord> om = mg.findById(dto.id); 
		BusinessChecks.exists(om, "The mechanic does not exist");
        MechanicRecord r = om.orElse(null);
		BusinessChecks.hasVersion(dto.version, r.version);

        if(mg.findByNif(dto.nif).isPresent())
            throw new BusinessException("The mechanic already exists");

		mg.update(MechanicDtoAssembler.toRecord(dto));

		return null;
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

package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.*;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ListMechanicByNif implements Command<Optional<MechanicDto>> {

    MechanicGateway mg = Factories.persistence.forMechanic();
	
	private final String nif;
	
	public ListMechanicByNif( String nif ) {
		ArgumentChecks.isNotNull(nif);
		ArgumentChecks.isNotBlank(nif);
		ArgumentChecks.isNotEmpty(nif);
		this.nif = nif;
	}
	
	public Optional<MechanicDto> execute() throws BusinessException {

        try {
            Optional<MechanicGateway.MechanicRecord> om = mg.findByNif(nif);
            BusinessChecks.exists(om);

            return Optional.of(MechanicDtoAssembler.toDto(om.get()));
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        } catch (BusinessException e) {
            return Optional.empty();
        }
		
	}

}

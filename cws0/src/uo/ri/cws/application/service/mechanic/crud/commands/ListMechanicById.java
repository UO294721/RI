package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class ListMechanicById implements Command<Optional<MechanicDto>> {
	
	private String id;
    private MechanicGateway mg = Factories.persistence.forMechanic();
	
	public ListMechanicById( String id ) {
		ArgumentChecks.isNotNull(id);
		ArgumentChecks.isNotBlank(id);
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}
	
	public Optional<MechanicDto> execute() throws BusinessException {
		
		try{
            Optional<MechanicGateway.MechanicRecord> mr = mg.findById(id);
            return mr.map(MechanicDtoAssembler::toDto);
        } catch (PersistenceException e) {
            throw new BusinessException("Error reading the mechanic");
        }
		
	}

}

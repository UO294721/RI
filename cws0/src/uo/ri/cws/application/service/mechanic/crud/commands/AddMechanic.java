package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class AddMechanic implements Command<MechanicDto> {
	
	private MechanicDto dto;
	private MechanicGateway mg = Factories.persistence.forMechanic();

    public AddMechanic( MechanicDto dto ) {
		
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.nif);
		ArgumentChecks.isNotBlank(dto.name);
		ArgumentChecks.isNotBlank(dto.surname);		
		dto.id = UUID.randomUUID().toString();
        dto.version = 1L;
		this.dto = dto;
		
	}

	public MechanicDto execute() throws BusinessException{
		
		try {
            if(mg.findByNif(dto.nif).isPresent())
                throw new BusinessException("The mechanic already exists");

            mg.add(MechanicRecordAssembler.toRecord(dto));
        } catch (PersistenceException e) {
            throw new BusinessException("Error adding the mechanic");
        }
		
		return dto;
		
	}

}

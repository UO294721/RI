package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.jdbc.Jdbc;

public class ListAllMechanics implements Command<List<MechanicDto>> {

    private final MechanicGateway mg = Factories.persistence.forMechanic();

	public List<MechanicDto> execute() {

        List<MechanicDto> mechanicDtos = new ArrayList<>();

        for(MechanicGateway.MechanicRecord mr : mg.findAll()) {
            mechanicDtos.add(MechanicDtoAssembler.toDto(mr));
        }

        return mechanicDtos;

	}
	
}

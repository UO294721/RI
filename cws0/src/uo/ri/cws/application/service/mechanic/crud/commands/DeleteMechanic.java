package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class DeleteMechanic implements Command<Void> {
	
	private String id;
    private MechanicGateway mg = Factories.persistence.forMechanic();
    private WorkOrderGateway wg = Factories.persistence.forWorkOrder();
	
	public DeleteMechanic(String id) {
		ArgumentChecks.isNotNull(id);
		this.id = id;
	}

	public Void execute() throws BusinessException {
		
        try {
            if(mg.findById(id).isEmpty())
                throw new BusinessException("The mechanic does not exist");

            mg.remove(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Error deleting the mechanic");
        }
		return null;
	}
	
}

package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
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
    private InterventionGateway ig = Factories.persistence.forIntervention();
    private ContractGateway cg = Factories.persistence.forContract();

	public DeleteMechanic(String id) {
		ArgumentChecks.isNotNull(id);
		this.id = id;
	}

	public Void execute() throws BusinessException {
		
        try {
            if(mg.findById(id).isEmpty())
                throw new BusinessException("The mechanic does not exist");
            if(wg.hasActiveWorkOrders(id))
                throw new BusinessException("The mechanic has active workorders");

            if(ig.hasInterventions(id))
                throw new BusinessException("There are interventions associated to the mechanic");

            if(cg.hasInForceContract(id))
                throw new BusinessException("There is a contract in force");

            if(cg.hasTerminatedContract(id))
                throw new BusinessException("The mechanic has a terminated contract");

            mg.remove(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Error deleting the mechanic");
        }
		return null;
	}
	
}

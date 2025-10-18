package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {
	
	private final String id;
    private final MechanicGateway mg = Factories.persistence.forMechanic();
    private final WorkOrderGateway wg = Factories.persistence.forWorkOrder();
    private final InterventionGateway ig = Factories.persistence.forIntervention();
    private final ContractGateway cg = Factories.persistence.forContract();

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

            if(cg.findInforceByMechanicId(id).isPresent())
                throw new BusinessException("There is a contract in force");

            if(!cg.findByMechanicId(id).isEmpty())
                throw new BusinessException("The mechanic has a terminated contract");

            mg.remove(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Error deleting the mechanic");
        }
		return null;
	}
	
}

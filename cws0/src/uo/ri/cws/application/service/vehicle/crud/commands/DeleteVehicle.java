package uo.ri.cws.application.service.vehicle.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteVehicle implements Command<Void> {
	
	private final String id;
	private final VehicleGateway vg = Factories.persistence.forVehicle();
	
	public DeleteVehicle( String id ) {
		ArgumentChecks.isNotNull(id);
		ArgumentChecks.isNotBlank(id);
		this.id = id;
	}
	
	@Override
	public Void execute() throws BusinessException {
		
		try{
			BusinessChecks.exists(vg.findById(id));
			vg.remove(id);
		} catch(PersistenceException e) {
			throw new BusinessException("Error deleting the vehicle");
		}
		
		return null;
	}
}

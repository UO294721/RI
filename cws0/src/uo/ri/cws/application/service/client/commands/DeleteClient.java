package uo.ri.cws.application.service.client.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class DeleteClient implements Command<Void> {
	
	private final String id;
	private final ClientGateway cg = Factories.persistence.forClient();
	private final VehicleGateway vg = Factories.persistence.forVehicle();
	
	public DeleteClient(String id) {
		ArgumentChecks.isNotBlank(id, "Client id cannot be blank");
		this.id = id;
	}
	
	@Override
	public Void execute() throws BusinessException {
		try {
			// Find client
			Optional<ClientRecord> ocr = cg.findById(id);
			BusinessChecks.exists(ocr, "Client does not exist");
			
			// Check if client has vehicles
			if(!vg.findByClientId(id).isEmpty())
				throw new BusinessException("Client has vehicles");
			
			// Delete the client
			cg.remove(id);
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error deleting client", e);
		}
	}
}
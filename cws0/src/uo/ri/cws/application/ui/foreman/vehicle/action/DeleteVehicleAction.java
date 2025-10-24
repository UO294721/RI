package uo.ri.cws.application.ui.foreman.vehicle.action;

import uo.ri.conf.Factories;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteVehicleAction implements Action {
	@Override
	public void execute() throws BusinessException {
		
		String id = Console.readString("Introduce the id of the vehicle to delete");
		
		Factories.service.forVehicleCrudService().delete(id);
		
		Console.println("Vehicle deleted");
		
	}
}

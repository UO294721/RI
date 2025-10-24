package uo.ri.cws.application.ui.foreman.vehicle.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.util.List;

public class ListAllVehiclesAction implements Action {
	
	
	@Override
	public void execute() throws Exception {
		
		VehicleCrudService vcs = Factories.service.forVehicleCrudService();
		
		List<VehicleDto> vehicles = vcs.findAll();
		
		if( vehicles.isEmpty() ) {
			Console.println("No vehicles found");
			return;
		}
		
		vcs.findAll().forEach(Printer::printVehicle);
		
	}
}

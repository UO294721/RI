package uo.ri.cws.application.ui.foreman.vehicle.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class UpdateVehicleAction implements Action {
	
	
	@Override
	public void execute() throws BusinessException {
		
		String plateNumber = Console.readString("Introduce the plate number " +
				                                        "of the vehicle to update");
		
		VehicleCrudService vcs = Factories.service.forVehicleCrudService();
		
		String make = Console.readString("Make");
		String model = Console.readString("Model");
		String type = Console.readString("Vehicle type");
		
		
		Optional<VehicleDto> odto = vcs.findByPlate(plateNumber);
		
		if(!odto.isPresent()) {
			Console.println("Vehicle not found");
			return;
		}
		
		VehicleDto dto = odto.get();
		
		dto.make = make;
		dto.model = model;
		dto.vehicleTypeId = type;
		vcs.update(dto);
		
		System.out.println("Vehicle updated successfully");
		
	}
}

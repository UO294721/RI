package uo.ri.cws.application.ui.foreman.vehicle.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class AddVehicleAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		String plateNumber = Console.readString("Plate number");
		String make = Console.readString("Make");
		String model = Console.readString("Model");
		String nif = Console.readString("Client nif");
		String type = Console.readString("Vehicle type");
		
		VehicleDto dto = new VehicleDto();
		dto.plate = plateNumber;
		dto.make = make;
		dto.model = model;
		dto.vehicleTypeId = type;
		
		ClientCrudService ccs = Factories.service.forClientCrudService();
		Optional<ClientDto> odto = ccs.findByNif(nif);
		
		if(!odto.isPresent()) {
			
			Console.println("New client, please enter the following data:");
			
			String name = Console.readString("Name");
			String surname = Console.readString("Surname");
			String street = Console.readString("Street");
			String city = Console.readString("City");
			String zipcode = Console.readString("ZipCode");
			String email = Console.readString("Email");
			String phone = Console.readString("Phone");
			
			ClientDto cdto = new ClientDto();
			cdto.name = name;
			cdto.surname = surname;
			cdto.nif = nif;
			cdto.addressStreet = street;
			cdto.addressCity = city;
			cdto.addressZipcode = zipcode;
			cdto.phone = phone;
			cdto.email = email;
			
			ccs.create(cdto);
			Console.println("Client created successfully");
			
		}
		
		dto.clientId = ccs.findByNif(nif).get().id;
		
		VehicleCrudService vcs = Factories.service.forVehicleCrudService();
		vcs.create(dto);
		
		Console.println("Vehicle added");
		
	}
}

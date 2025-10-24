package uo.ri.cws.application.ui.foreman.client.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class AddClientAction implements Action {
	@Override
	public void execute() throws Exception {
	
		String name = Console.readString("Name");
		String surname = Console.readString("Surname");
		String nif = Console.readString("Nif");
		String street = Console.readString("Street");
		String city = Console.readString("City");
		String zipcode = Console.readString("ZipCode");
		String email = Console.readString("Email");
		String phone = Console.readString("Phone");
		
		ClientDto dto = new ClientDto();
		dto.name = name;
		dto.surname = surname;
		dto.nif = nif;
		dto.addressStreet = street;
		dto.addressCity = city;
		dto.addressZipcode = zipcode;
		dto.phone = phone;
		dto.email = email;
		
		ClientCrudService ccs = Factories.service.forClientCrudService();
		
		ccs.create(dto);
		
		Console.println("Client created successfully");
	
	}
}

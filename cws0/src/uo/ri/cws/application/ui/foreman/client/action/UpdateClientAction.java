package uo.ri.cws.application.ui.foreman.client.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class UpdateClientAction implements Action {
	@Override
	public void execute() throws Exception {
		
		ClientCrudService ccs = Factories.service.forClientCrudService();
		
		String nif =
				Console.readString("Introduce the nif of the client to update");
		
		Optional<ClientDto> ocdto = ccs.findByNif(nif);
		
		if (!ocdto.isPresent()) {
			Console.println("Client not found");
			return;
		}
		
		ClientDto dto = ocdto.get();
		
		String name = Console.readString("Name [optional]");
		String surname = Console.readString("Surname [optional]");
		String newNif = Console.readString("Nif [optional]");
		String street = Console.readString("Street [optional]");
		String city = Console.readString("City [optional]");
		String zipcode = Console.readString("ZipCode [optional]");
		String email = Console.readString("Email [optional]");
		String phone = Console.readString("Phone [optional]");
		
		if( name != null && !name.isEmpty()) dto.name = name;
		if( surname != null && !surname.isEmpty()) dto.surname = surname;
		if( newNif != null && !newNif.isEmpty()) dto.nif = newNif;
		if( street != null && !street.isEmpty()) dto.addressStreet = street;
		if( city != null && !city.isEmpty()) dto.addressCity = city;
		if( zipcode != null && !zipcode.isEmpty()) dto.addressZipcode = zipcode;
		if( email != null && !email.isEmpty()) dto.email = email;
		if( phone != null && !phone.isEmpty()) dto.phone = phone;
		
		ccs.update(dto);
		
		Console.println("Client updated successfully");
	
	}
	
}

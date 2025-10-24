package uo.ri.cws.application.ui.foreman.client.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.util.List;

public class ListAllClientsAction implements Action {
	@Override
	public void execute() throws Exception {
		
		List<ClientDto> clients =
				Factories.service.forClientCrudService().findAll();
		
		if( clients.isEmpty() ) {
			Console.println("No clients found");
		} else {
			clients.forEach(Printer::printClient);
		}
	
	}
}

package uo.ri.cws.application.ui.foreman.client.action;

import uo.ri.conf.Factories;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteClientAction implements Action {
	@Override
	public void execute() throws BusinessException {
		
		String id = Console.readString("Introduce the id of the client to " +
				                              "delete");
		
		Factories.service.forClientCrudService().delete(id);
		
		Console.println("Client deleted");
	
	}
}

package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteContractAction implements Action {

    @Override
    public void execute() {

        String id = Console.readString("Contract id");
		try {
			Factories.service.forContractCrudService().delete(id);
		} catch (BusinessException e) {
			Console.println("Couldnt delete the mechanic");
			return;
		} catch (IllegalArgumentException e) {
			Console.println("The id is not valid");
			return;
		}
		
		Console.println("The contract has been deleted");
    }

}

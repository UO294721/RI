package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import uo.ri.conf.Factories;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteContractTypeAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Contract type name");
		
		Factories.service.forContractTypeCrudService().delete(name);
		
		Console.println("The contract type has been deleted");
    }

}
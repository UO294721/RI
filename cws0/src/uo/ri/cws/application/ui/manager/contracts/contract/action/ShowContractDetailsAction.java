package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class ShowContractDetailsAction implements Action {

    @Override
    public void execute() throws BusinessException{
        String id = Console.readString("Contract id");
		Optional<ContractDto> contract =
				Factories.service.forContractCrudService().findById(id);
		Printer.printContractDetails(contract.get());

    }
}
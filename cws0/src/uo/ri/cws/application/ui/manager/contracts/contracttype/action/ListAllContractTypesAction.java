package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.util.List;

public class ListAllContractTypesAction implements Action {

    @Override
    public void execute() throws Exception {
		
		ContractTypeCrudService cts = Factories.service.forContractTypeCrudService();
		
		List<ContractTypeDto> types = cts.findAll();

		if (types.isEmpty()) {
			Console.println("No contract types found");
			return;
		}

		for (ContractTypeDto dto : types) {
			Printer.printContractType(dto);
		}
    }

}
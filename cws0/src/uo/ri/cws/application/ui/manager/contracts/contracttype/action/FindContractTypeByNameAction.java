package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class FindContractTypeByNameAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Contract type name");
		
		ContractTypeCrudService cts = Factories.service.forContractTypeCrudService();
		
		Optional<ContractTypeDto> dto = cts.findByName(name);
		
		if(dto.isPresent()) {
			Printer.printContractType(dto.get());
			return;
		}
		else {
			Console.println("Contract type not found");
			return;
		}
    }

}
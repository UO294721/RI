package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddContractTypeAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Contract type name");
        double compensationDays = Console.readDouble("Compensation days");
	    
	    ContractTypeDto dto = new ContractTypeDto();
		dto.name = name;
		dto.compensationDays = compensationDays;
		
        Factories.service.forContractTypeCrudService()
		        .create(dto);

        Console.println("Contract type registered");
    }

}
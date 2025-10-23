package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateContractTypeAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Contract type name");

        ContractTypeCrudService cts = Factories.service.forContractTypeCrudService();
		
		ContractTypeDto dto = cts.findByName(name).get();
	    
	    dto.compensationDays = Console.readDouble("Compensation days");
		
		cts.update(dto);

		Console.println("Contract type updated");
    }

}
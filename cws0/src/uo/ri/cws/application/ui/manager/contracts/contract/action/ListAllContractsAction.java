package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.List;

public class ListAllContractsAction implements Action {

    @Override
    public void execute() throws BusinessException {
	    
	    ContractCrudService ccs = Factories.service.forContractCrudService();
		
		List<ContractSummaryDto> contracts = ccs.findAll();
		
         if (contracts.isEmpty()) { Console.println("No contracts found");
         return; }
         
         for (ContractSummaryDto c : contracts) {
         Printer.printContractSummary(c); }
        
    }

}
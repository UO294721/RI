package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.List;

public class ListContractsOfMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

        String nif = Console.readString("Mechanic nif");

        //throw new UnsupportedOperationException("Not yet implemented");
	    
	    ContractCrudService ccs = Factories.service.forContractCrudService();
        
         List<ContractSummaryDto> contracts = ccs.findByMechanicNif( nif );
         
         for(ContractSummaryDto c: contracts) { Printer.printContractSummary(
         c ); }
        
    }
}

package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.time.LocalDate;
import java.util.Optional;

public class UpdateContractAction implements Action {

    @Override
    public void execute() throws BusinessException {

        String id = Console.readString("Contract id");

        // Find contract by id
	    ContractCrudService ccs = Factories.service.forContractCrudService();
		Optional<ContractDto> odto = ccs.findById(id);
		
		ContractDto dto = odto.get();
		
	    LocalDate endDate = askOptionalForDate("End date");
		
		if(endDate != null)
			dto.endDate = endDate;
		
		dto.annualBaseSalary = askBaseSalary();
		
		ccs.update(dto);

		Console.println("Contract updated");
    }
	
	private double askBaseSalary() {
        while (true) {
            try {
                Console.print("Base salary: ");
                return Console.readDouble();
            } catch (Exception e) {
                Console.println("--> Invalid base salary");
            }
        }
    }
	
    private LocalDate askOptionalForDate(String msg) {
        while (true) {
            try {
                Console.print(msg + " [optional]: ");
                String asString = Console.readString();
                return ("".equals(asString)) ? null : LocalDate.parse(asString);
            } catch (Exception e) {
                Console.println("--> Invalid date");
            }
        }
    }
}
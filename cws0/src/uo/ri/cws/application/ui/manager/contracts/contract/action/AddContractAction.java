package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractTypeOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.MechanicOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ProfessionalGroupOfContractDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.time.LocalDate;

public class AddContractAction implements Action {

    /**
     * Creates a contract. The mechanic's NIF, contract type name, type name,
     * month and base salary are provided via console.
     */
    @Override
    public void execute() {
	    String nif = Console.readString("Mechanic NIF");
	    String contractTypeName = askForType();
	    String professionalGroupName = askForProfessionalGroup();
	    double annualBaseSalary = Console.readDouble("Annual base salary");
	    
	    // Create DTO
	    ContractDto dto = new ContractDto();
	    dto.mechanic = new MechanicOfContractDto();
	    dto.mechanic.nif = nif;
	    dto.contractType = new ContractTypeOfContractDto();
	    dto.contractType.name = contractTypeName;
	    dto.professionalGroup = new ProfessionalGroupOfContractDto();
	    dto.professionalGroup.name = professionalGroupName;
	    dto.annualBaseSalary = annualBaseSalary;
		dto.startDate = LocalDate.of(LocalDate.now().getYear(),
				LocalDate.now().getMonth().plus(1),
				1);
	    
	    if ("FIXED_TERM".equals(contractTypeName)) {
		    dto.endDate = Console.readDate("Type end date");
	    }
	    
	    ContractCrudService service = Factories.service.forContractCrudService();
		try {
			ContractDto created = service.create(dto);
			Console.println("Contract registered with ID: " + created.id);
		} catch (BusinessException e) {
			Console.println(e.getMessage());
			return;
		}
	    
    }

    private String askForProfessionalGroup() {
        Console.println("Professional group");
        Console.println("I \t II \t III \t IV \t V \t VI \t VII");
        return Console.readString("Professional group name");
    }

    private String askForType() {
        Console.println("Contract type");
        Console.println("PERMANENT \t SEASONAL \t FIXED_TERM");
        return Console.readString("Contract type name");
    }

}
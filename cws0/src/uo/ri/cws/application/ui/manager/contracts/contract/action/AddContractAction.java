package uo.ri.cws.application.ui.manager.contracts.contract.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddContractAction implements Action {

    /**
     * Creates a contract. The mechanic's NIF, contract type name, type name,
     * month and base salary are provided via console.
     */
    @Override
    public void execute() throws BusinessException {
	    String nif = Console.readString("Mechanic NIF");
	    String contractTypeName = askForType();
	    String professionalGroupName = askForProfessionalGroup();
	    double annualBaseSalary = Console.readDouble("Annual base salary");
	    
	    // Create DTO
	    ContractCrudService.ContractDto dto = new ContractCrudService.ContractDto();
	    dto.mechanic = new ContractCrudService.MechanicOfContractDto();
	    dto.mechanic.nif = nif;
	    dto.contractType = new ContractCrudService.ContractTypeOfContractDto();
	    dto.contractType.name = contractTypeName;
	    dto.professionalGroup = new ContractCrudService.ProfessionalGroupOfContractDto();
	    dto.professionalGroup.name = professionalGroupName;
	    dto.annualBaseSalary = annualBaseSalary;
	    
	    if ("FIXED_TERM".equals(contractTypeName)) {
		    dto.endDate = Console.readDate("Type end date");
	    }
	    
	    // ✅ USE SERVICE
	    ContractCrudService service = Factories.service.forContractCrudService();
	    ContractCrudService.ContractDto created = service.create(dto);
	    
	    Console.println("Contract registered with ID: " + created.id);
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
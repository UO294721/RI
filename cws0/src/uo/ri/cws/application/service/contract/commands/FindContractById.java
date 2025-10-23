package uo.ri.cws.application.service.contract.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class FindContractById implements Command<Optional<ContractDto>> {
	
	private String id;
	private final ContractGateway cg = Factories.persistence.forContract();
	private final MechanicGateway mg = Factories.persistence.forMechanic();
	private final ContractTypeGateway ctg = Factories.persistence.forContractType();
	private final ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
	
	public FindContractById(String id) {
		ArgumentChecks.isNotBlank(id, "Contract id cannot be blank");
		this.id = id;
	}
	
	@Override
	public Optional<ContractDto> execute(){
		try {
			Optional<ContractRecord> ocr = cg.findById(id);
			
			if( ocr.isEmpty())
				return Optional.empty();
			
			ContractRecord cr = ocr.get();
			
			// Load related entities
			MechanicRecord mr = mg.findById(cr.mechanicId).orElseThrow();
			ContractTypeRecord ctr = ctg.findById(cr.contractTypeId).orElseThrow();
			ProfessionalGroupRecord pgr = pgg.findById(cr.professionalGroupId).orElseThrow();
			
			ContractDto dto = ContractDtoAssembler.toDto(cr, mr, ctr, pgr);
			
			return Optional.of(dto);
			
		} catch (PersistenceException e) {
		}
		return Optional.empty();
	}
}
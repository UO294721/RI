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
import uo.ri.util.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class FindInforceContracts implements Command<List<ContractDto>> {
	
	private final ContractGateway cg = Factories.persistence.forContract();
	private final MechanicGateway mg = Factories.persistence.forMechanic();
	private final ContractTypeGateway ctg = Factories.persistence.forContractType();
	private final ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
	
	@Override
	public List<ContractDto> execute() throws BusinessException {
		try {
			List<ContractRecord> contracts = cg.findInforceContracts();
			List<ContractDto> result = new ArrayList<>();
			
			for (ContractRecord cr : contracts) {
				MechanicRecord mr = mg.findById(cr.mechanicId).orElseThrow();
				ContractTypeRecord ctr = ctg.findById(cr.contractTypeId).orElseThrow();
				ProfessionalGroupRecord pgr = pgg.findById(cr.professionalGroupId).orElseThrow();
				
				result.add(ContractDtoAssembler.toDto(cr, mr, ctr, pgr));
			}
			
			return result;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error finding in-force contracts", e);
		}
	}
}
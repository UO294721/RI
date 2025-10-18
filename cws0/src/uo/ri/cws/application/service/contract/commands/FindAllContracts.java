package uo.ri.cws.application.service.contract.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.service.contract.ContractDtoAssembler;
import uo.ri.util.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class FindAllContracts implements Command<List<ContractSummaryDto>> {
	
	private final ContractGateway cg = Factories.persistence.forContract();
	private final MechanicGateway mg = Factories.persistence.forMechanic();
	
	@Override
	public List<ContractSummaryDto> execute() throws BusinessException {
		try {
			List<ContractRecord> contracts = cg.findAll();
			List<ContractSummaryDto> result = new ArrayList<>();
			
			for (ContractRecord cr : contracts) {
				MechanicRecord mr = mg.findById(cr.mechanicId).orElseThrow();
				int numPayrolls = cg.countPayrollsByContractId(cr.id);
				
				result.add(ContractDtoAssembler.toSummaryDto(cr, mr.nif, numPayrolls));
			}
			
			return result;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error finding all contracts", e);
		}
	}
}
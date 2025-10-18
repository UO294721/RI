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
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindContractsByMechanicNif implements Command<List<ContractSummaryDto>> {
	
	private String nif;
	private final ContractGateway cg = Factories.persistence.forContract();
	private final MechanicGateway mg = Factories.persistence.forMechanic();
	
	public FindContractsByMechanicNif(String nif) {
		ArgumentChecks.isNotBlank(nif, "Mechanic NIF cannot be blank");
		this.nif = nif;
	}
	
	@Override
	public List<ContractSummaryDto> execute() throws BusinessException {
		try {
			// Find mechanic
			Optional<MechanicRecord> omr = mg.findByNif(nif);
			if (omr.isEmpty()) {
				return new ArrayList<>();
			}
			
			MechanicRecord mr = omr.get();
			
			// Find all contracts for this mechanic
			List<ContractRecord> contracts = cg.findByMechanicId(mr.id);
			List<ContractSummaryDto> result = new ArrayList<>();
			
			for (ContractRecord cr : contracts) {
				int numPayrolls = cg.countPayrollsByContractId(cr.id);
				result.add(ContractDtoAssembler.toSummaryDto(cr, mr.nif, numPayrolls));
			}
			
			return result;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error finding contracts by mechanic", e);
		}
	}
}
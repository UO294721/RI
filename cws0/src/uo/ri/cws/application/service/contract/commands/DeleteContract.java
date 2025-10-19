package uo.ri.cws.application.service.contract.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class DeleteContract implements Command<Void> {
	
	private final String id;
	private final ContractGateway cg = Factories.persistence.forContract();
	private final InterventionGateway ig =
			Factories.persistence.forIntervention();
	
	public DeleteContract(String id) {
		ArgumentChecks.isNotBlank(id, "Contract id cannot be blank");
		this.id = id;
	}
	
	@Override
	public Void execute() throws BusinessException {
		try {
			// 1. Find contract
			Optional<ContractRecord> ocr = cg.findById(id);
			BusinessChecks.exists(ocr, "Contract does not exist");
			
			// 2. Check no payrolls exist for this contract
			int payrollCount = cg.countPayrollsByContractId(id);
			BusinessChecks.isTrue(payrollCount == 0,
					"Cannot delete contract with payrolls");
			
			BusinessChecks.isFalse(ig.hasInterventions(ocr.get().mechanicId));
			
			// 3. Delete the contract
			cg.remove(id);
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error deleting contract", e);
		}
	}
}
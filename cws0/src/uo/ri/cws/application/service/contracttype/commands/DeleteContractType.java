package uo.ri.cws.application.service.contracttype.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

/**
 * Command to delete a contract type.
 * Follows the Transaction Script pattern (slide 73-76).
 */
public class DeleteContractType implements Command<Void> {
	
	private final String name;
	private final ContractTypeGateway gateway = Factories.persistence.forContractType();
	private final ContractGateway cg = Factories.persistence.forContract();
	
	public DeleteContractType(String name) {
		ArgumentChecks.isNotBlank(name, "Contract type name cannot be blank");
		this.name = name;
	}
	
	@Override
	public Void execute() throws BusinessException {
		try {
			// 1. Find contract type
			Optional<ContractTypeRecord> record = gateway.findByName(name);
			BusinessChecks.exists(record, "Contract type does not exist");
			
			ContractTypeRecord ctr = record.get();
			
			// 2. Check if has contracts (using the contract type ID, not name)
			Optional<ContractGateway.ContractRecord> existingContract =
					cg.findByContractTypeId(ctr.id);
			
			if (existingContract.isPresent()) {
				throw new BusinessException(
						"Cannot delete contract type: there are contracts associated with it");
			}
			
			// 3. Delete the contract type
			gateway.remove(ctr.id);
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error deleting contract type", e);
		}
	}
}
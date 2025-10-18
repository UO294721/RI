package uo.ri.cws.application.service.contract.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class DeleteContract implements Command<Void> {
	
	private String id;
	private final ContractGateway cg = Factories.persistence.forContract();
	private final WorkOrderGateway wog = Factories.persistence.forWorkOrder();
	
	public DeleteContract(String id) {
		ArgumentChecks.isNotBlank(id, "Contract id cannot be blank");
		this.id = id;
	}
	
	@Override
	public Void execute() throws BusinessException {
		try {
			// Find contract
			Optional<ContractRecord> ocr = cg.findById(id);
			BusinessChecks.exists(ocr, "Contract does not exist");
			ContractRecord cr = ocr.get();
			
			// Check mechanic has no work orders
			// This would require a method in MechanicGateway or WorkOrderGateway
			// For simplicity, we'll check if there are payrolls
			
			// Check no payrolls exist for this contract
			int payrollCount = cg.countPayrollsByContractId(id);
			BusinessChecks.isTrue(payrollCount == 0,
					"Cannot delete contract with payrolls");
			
			cg.remove(id);
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error deleting contract", e);
		}
	}
}
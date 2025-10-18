package uo.ri.cws.application.service.contract.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.time.LocalDate;
import java.util.Optional;

public class TerminateContract implements Command<Void> {
	
	private String contractId;
	private final ContractGateway cg = Factories.persistence.forContract();
	private final ContractTypeGateway ctg = Factories.persistence.forContractType();
	
	public TerminateContract(String contractId) {
		ArgumentChecks.isNotBlank(contractId, "Contract id cannot be blank");
		this.contractId = contractId;
	}
	
	@Override
	public Void execute() throws BusinessException {
		try {
			// Find contract
			Optional<ContractRecord> ocr = cg.findById(contractId);
			BusinessChecks.exists(ocr, "Contract does not exist");
			ContractRecord cr = ocr.get();
			
			// Check contract is in force
			BusinessChecks.isTrue("IN_FORCE".equals(cr.state),
					"Contract is not in force");
			
			// Find contract type for settlement calculation
			Optional<ContractTypeRecord> octr = ctg.findById(cr.contractTypeId);
			BusinessChecks.exists(octr, "Contract type does not exist");
			ContractTypeRecord ctr = octr.get();
			
			// Calculate settlement
			cr.settlement = calculateSettlement(cr, ctr);
			cr.state = "TERMINATED";
			
			cg.update(cr);
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error terminating contract", e);
		}
	}
	
	private double calculateSettlement(ContractRecord cr, ContractTypeRecord ctr) {
		// Calculate settlement based on compensation days
		double dailySalary = cr.annualBaseSalary / 365.0;
		long daysWorked = java.time.temporal.ChronoUnit.DAYS.between(
				cr.startDate, LocalDate.now());
		double yearsWorked = daysWorked / 365.0;
		
		return dailySalary * ctr.compensationDays * yearsWorked;
	}
}
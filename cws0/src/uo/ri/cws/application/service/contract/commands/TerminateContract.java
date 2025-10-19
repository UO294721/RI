package uo.ri.cws.application.service.contract.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class TerminateContract implements Command<Void> {
	
	private String contractId;
	private final ContractGateway cg = Factories.persistence.forContract();
	private final ContractTypeGateway ctg = Factories.persistence.forContractType();
	private final PayrollGateway pg = Factories.persistence.forPayroll();
	private final MechanicGateway mg = Factories.persistence.forMechanic();
	
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
			
			if( ChronoUnit.DAYS.between(cr.startDate, LocalDate.now()) < 365 )
				cr.settlement = 0.0;
			else {
				// Find contract type for settlement calculation
				Optional<ContractTypeRecord> octr = ctg.findById(cr.contractTypeId);
				BusinessChecks.exists(octr, "Contract type does not exist");
				ContractTypeRecord ctr = octr.get();
				
				// Calculate settlement
				cr.settlement = calculateSettlement(cr, ctr);
			}
			cr.state = "TERMINATED";
			cr.endDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
					LocalDate.MAX.getDayOfMonth());
			
			cg.update(cr);
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error terminating contract", e);
		}
	}
	
	private double calculateSettlement(ContractRecord cr, ContractTypeRecord ctr) {
		
		// Check if at least 365 days have passed
		long daysWorked = ChronoUnit.DAYS.between(cr.startDate, LocalDate.now());
		if (daysWorked < 365) {
			return 0.0; // No settlement if less than 1 year
		}
		
		// Get last 12 payrolls for this contract
		List<PayrollRecord> lastPayrolls =
				pg.findLast12ByContractId(cr.id);
		
		// If no payrolls exist, cannot calculate settlement
		if (lastPayrolls.isEmpty()) {
			return 0.0;
		}
		
		// A) Calculate average daily gross salary from last 12 months
		double totalGrossSalary = lastPayrolls.stream()
				.mapToDouble(p -> p.baseSalary + p.extraSalary +
						p.productivityEarning + p.trienniumEarning)
				.sum();
		double averageDailyGrossSalary = totalGrossSalary / 365.0;
		
		// B) Compensation days per year
		double compensationDays = ctr.compensationDays;
		
		// C) Number of FULL years worked
		long fullYears = daysWorked / 365;
		
		// Settlement = A × B × C
		return averageDailyGrossSalary * compensationDays * fullYears;
	}
}
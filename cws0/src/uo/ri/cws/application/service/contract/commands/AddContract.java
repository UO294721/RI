package uo.ri.cws.application.service.contract.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddContract implements Command<ContractDto> {
	
	private final ContractDto dto;
	private final ContractGateway cg = Factories.persistence.forContract();
	private final MechanicGateway mg = Factories.persistence.forMechanic();
	private final ContractTypeGateway ctg = Factories.persistence.forContractType();
	private final ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
	private final PayrollGateway pg = Factories.persistence.forPayroll();
	
	
	public AddContract(ContractDto dto) {
		ArgumentChecks.isNotNull(dto, "Contract dto cannot be null");
		ArgumentChecks.isNotBlank(dto.mechanic.nif, "Mechanic NIF cannot be blank");
		ArgumentChecks.isNotBlank(dto.contractType.name, "Contract type name cannot be blank");
		ArgumentChecks.isNotBlank(dto.professionalGroup.name, "Professional group name cannot be blank");
		ArgumentChecks.isNotNull(dto.startDate, "Start date cannot be null");
		ArgumentChecks.isTrue(dto.annualBaseSalary > 0, "Annual salary must be positive");
		
		// Initialize DTO fields
		dto.id = UUID.randomUUID().toString();
		dto.version = 1L;
		this.dto = dto;
	}
	
	@Override
	public ContractDto execute() throws BusinessException {
		try {
			// 1. Find and validate mechanic
			Optional<MechanicRecord> omr = mg.findByNif(dto.mechanic.nif);
			BusinessChecks.exists(omr, "Mechanic does not exist");
			MechanicRecord mr = omr.get();
			dto.mechanic = ContractDtoAssembler.toMechanicDto(mr);
			
			// 2. Find and validate contract type
			Optional<ContractTypeRecord> octr = ctg.findByName(dto.contractType.name);
			BusinessChecks.exists(octr, "Contract type does not exist");
			ContractTypeRecord ctr = octr.get();
			dto.contractType = ContractDtoAssembler.toContractTypeDto(ctr);
			
			// 3. Find and validate professional group
			Optional<ProfessionalGroupRecord> opgr = pgg.findByName(dto.professionalGroup.name);
			BusinessChecks.exists(opgr, "Professional group does not exist");
			ProfessionalGroupRecord pgr = opgr.get();
			dto.professionalGroup = ContractDtoAssembler.toProfessionalGroupDto(pgr);
			
			// 4. Validate FIXED_TERM contract has end date
			if ("FIXED_TERM".equals(ctr.name)) {
				ArgumentChecks.isNotNull(dto.endDate,
						"FIXED_TERM contracts must have an end date");
			}
			
			// 5. Validate end date if provided
			if (dto.endDate != null) {
				BusinessChecks.isTrue(dto.endDate.isAfter(dto.startDate),
						"End date must be after start date");
			}
			
			// 6. Check if mechanic has an active contract
			Optional<ContractRecord> existingContract = cg.findInforceByMechanicId(mr.id);
			if (existingContract.isPresent()) {
				// Terminate existing contract
				ContractRecord oldContract = existingContract.get();
				oldContract.state = "TERMINATED";
				
				// Get the OLD contract's type for settlement calculation
				Optional<ContractTypeRecord> oldCtr = ctg.findById(oldContract.contractTypeId);
				if (oldCtr.isPresent()) {
					oldContract.settlement = calculateSettlement(oldContract, oldCtr.get());
					oldContract.endDate =
							LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
									LocalDate.MAX.getDayOfMonth());
				}
				cg.update(oldContract);
			}
			
			// 7. Set start date to first day of next month
			LocalDate today = LocalDate.now();
			dto.startDate = today.plusMonths(1).withDayOfMonth(1);
			
			// 8. Calculate tax rate based on salary
			dto.taxRate = calculateTaxRate(dto.annualBaseSalary);
			
			// 9. Create and persist new contract
			ContractRecord cr = ContractDtoAssembler.toRecord(dto);
			cg.add(cr);
			
			// 10. Return fully populated DTO
			return ContractDtoAssembler.toDto(cr, dto.mechanic,
					dto.contractType, dto.professionalGroup);
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error creating contract", e);
		}
	}
	
	/**
	 * Calculate settlement for terminated contract
	 */
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
				.mapToDouble(p -> p.baseSalary)
				.sum();
		double averageDailyGrossSalary = totalGrossSalary / 365.0;
		
		// B) Compensation days per year
		double compensationDays = ctr.compensationDays;
		
		// C) Number of FULL years worked
		long fullYears = daysWorked / 365;
		
		// Settlement = A × B × C
		return averageDailyGrossSalary * compensationDays * fullYears;
	}
	
	/**
	 * Calculate tax rate based on annual salary
	 */
	private double calculateTaxRate(double annualBaseSalary) {
		if (annualBaseSalary < 12450) {
			return 0.19;
		} else if (annualBaseSalary < 20200) {
			return 0.24;
		} else if (annualBaseSalary < 35200){
			return 0.30;
		} else if(annualBaseSalary < 60000) {
			return 0.37;
		} else if(annualBaseSalary < 300000) {
			return 0.45;
		} else {
			return 0.47;
		}
	}
}
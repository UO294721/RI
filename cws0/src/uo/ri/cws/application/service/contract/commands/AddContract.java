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
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class AddContract implements Command<ContractDto> {
	
	private ContractDto dto;
	private final ContractGateway cg = Factories.persistence.forContract();
	private final MechanicGateway mg = Factories.persistence.forMechanic();
	private final ContractTypeGateway ctg = Factories.persistence.forContractType();
	private final ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
	
	public AddContract(ContractDto dto) {
		validateDto(dto);
		this.dto = dto;
	}
	
	private void validateDto(ContractDto dto) {
		ArgumentChecks.isNotNull(dto, "Contract dto cannot be null");
		ArgumentChecks.isNotBlank(dto.mechanic.nif, "Mechanic NIF cannot be blank");
		ArgumentChecks.isNotBlank(dto.contractType.name, "Contract type name cannot be blank");
		ArgumentChecks.isNotBlank(dto.professionalGroup.name, "Professional group name cannot be blank");
		ArgumentChecks.isNotNull(dto.startDate, "Start date cannot be null");
		ArgumentChecks.isTrue(dto.annualBaseSalary > 0, "Annual salary must be positive");
		
		if (dto.endDate != null) {
			ArgumentChecks.isTrue(dto.endDate.isAfter(dto.startDate),
					"End date must be after start date");
		}
	}
	
	@Override
	public ContractDto execute() throws BusinessException {
		try {
			// Find mechanic
			Optional<MechanicRecord> omr = mg.findByNif(dto.mechanic.nif);
			BusinessChecks.exists(omr, "Mechanic does not exist");
			MechanicRecord mr = omr.get();
			
			// Find contract type
			Optional<ContractTypeRecord> octr = ctg.findByName(dto.contractType.name);
			BusinessChecks.exists(octr, "Contract type does not exist");
			ContractTypeRecord ctr = octr.get();
			
			// Find professional group
			Optional<ProfessionalGroupRecord> opgr = pgg.findByName(dto.professionalGroup.name);
			BusinessChecks.exists(opgr, "Professional group does not exist");
			ProfessionalGroupRecord pgr = opgr.get();
			
			// Check if mechanic has an active contract
			Optional<ContractRecord> existingContract = cg.findInforceByMechanicId(mr.id);
			if (existingContract.isPresent()) {
				// Terminate existing contract
				ContractRecord oldContract = existingContract.get();
				oldContract.state = "TERMINATED";
				oldContract.settlement = calculateSettlement(oldContract, ctr);
				cg.update(oldContract);
			}
			
			// Validate FIXED_TERM contract has end date
			if ("FIXED_TERM".equals(ctr.name)) {
				BusinessChecks.isNotNull(dto.endDate,
						"FIXED_TERM contracts must have an end date");
			}
			
			// Create new contract record
			ContractRecord cr = new ContractRecord();
			cr.id = UUID.randomUUID().toString();
			cr.version = 1L;
			cr.mechanicId = mr.id;
			cr.contractTypeId = ctr.id;
			cr.professionalGroupId = pgr.id;
			
			// Set start date to first day of next month
			LocalDate today = LocalDate.now();
			cr.startDate = today.plusMonths(1).withDayOfMonth(1);
			cr.endDate = dto.endDate;
			
			cr.annualBaseSalary = dto.annualBaseSalary;
			cr.taxRate = 15.0; // Default tax rate
			cr.settlement = 0.0;
			cr.state = "IN_FORCE";
			
			cg.add(cr);
			
			return ContractDtoAssembler.toDto(cr, mr, ctr, pgr);
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error creating contract", e);
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
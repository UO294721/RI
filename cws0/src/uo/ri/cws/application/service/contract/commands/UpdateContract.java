package uo.ri.cws.application.service.contract.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class UpdateContract implements Command<Void> {
	
	private ContractDto dto;
	private final ContractGateway cg = Factories.persistence.forContract();
	
	public UpdateContract(ContractDto dto) {
		validateDto(dto);
		this.dto = dto;
	}
	
	private void validateDto(ContractDto dto) {
		ArgumentChecks.isNotNull(dto, "Contract dto cannot be null");
		ArgumentChecks.isNotBlank(dto.id, "Contract id cannot be blank");
		ArgumentChecks.isTrue(dto.annualBaseSalary > 0,
				"Annual salary cannot be negative");
	}
	
	@Override
	public Void execute() throws BusinessException {
		try {
			// Find contract
			Optional<ContractRecord> ocr = cg.findById(dto.id);
			BusinessChecks.exists(ocr, "Contract does not exist");
			ContractRecord cr = ocr.get();
			
			// Check contract is in force
			BusinessChecks.isTrue("IN_FORCE".equals(cr.state),
					"Contract is not in force");
			BusinessChecks.hasVersion(dto.version, cr.version);
			
			// Validate end date is after start date
			if (dto.endDate != null) {
				BusinessChecks.isTrue(dto.endDate.isAfter(cr.startDate),
						"End date must be after start date");
			}
			
			// Update fields
			cr.endDate = dto.endDate;
			cr.annualBaseSalary = dto.annualBaseSalary;
			
			cg.update(cr);
			
			return null;
			
		} catch (PersistenceException e) {
			throw new BusinessException("Error updating contract", e);
		}
	}
}
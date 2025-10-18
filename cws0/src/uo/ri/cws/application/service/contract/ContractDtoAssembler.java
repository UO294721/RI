package uo.ri.cws.application.service.contract;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class ContractDtoAssembler {
	
	/**
	 * Converts a DTO to a Record for persistence
	 */
	public static ContractRecord toRecord(ContractDto dto, String mechanicId,
	                                      String contractTypeId, String professionalGroupId) {
		ContractRecord cr = new ContractRecord();
		
		cr.id = dto.id != null ? dto.id : UUID.randomUUID().toString();
		cr.version = dto.version > 0 ? dto.version : 1L;
		
		cr.mechanicId = mechanicId;
		cr.contractTypeId = contractTypeId;
		cr.professionalGroupId = professionalGroupId;
		
		cr.startDate = dto.startDate;
		cr.endDate = dto.endDate;
		cr.annualBaseSalary = dto.annualBaseSalary;
		cr.taxRate = dto.taxRate > 0 ? dto.taxRate : 15.0;
		cr.settlement = dto.settlement;
		cr.state = dto.state != null ? dto.state : "IN_FORCE";
		
		cr.createdAt = LocalDateTime.now();
		cr.updatedAt = LocalDateTime.now();
		cr.entityState = "ENABLED";
		
		return cr;
	}
	
	/**
	 * Converts persistence records to a full DTO
	 */
	public static ContractDto toDto(ContractRecord cr, MechanicRecord mr,
	                                ContractTypeRecord ctr, ProfessionalGroupRecord pgr) {
		ContractDto dto = new ContractDto();
		
		dto.id = cr.id;
		dto.version = cr.version;
		dto.startDate = cr.startDate;
		dto.endDate = cr.endDate;
		dto.annualBaseSalary = cr.annualBaseSalary;
		dto.taxRate = cr.taxRate;
		dto.settlement = cr.settlement;
		dto.state = cr.state;
		
		// Mechanic info
		dto.mechanic.id = mr.id;
		dto.mechanic.nif = mr.nif;
		dto.mechanic.name = mr.name;
		dto.mechanic.surname = mr.surname;
		
		// Contract type info
		dto.contractType.id = ctr.id;
		dto.contractType.name = ctr.name;
		dto.contractType.compensationDaysPerYear = ctr.compensationDays;
		
		// Professional group info
		dto.professionalGroup.id = pgr.id;
		dto.professionalGroup.name = pgr.name;
		dto.professionalGroup.trieniumPayment = pgr.trienniumPayment;
		dto.professionalGroup.productivityRate = pgr.productivityRate;
		
		return dto;
	}
	
	/**
	 * Converts records to a summary DTO
	 */
	public static ContractSummaryDto toSummaryDto(ContractRecord cr,
	                                              String mechanicNif, int numPayrolls) {
		ContractSummaryDto dto = new ContractSummaryDto();
		
		dto.id = cr.id;
		dto.nif = mechanicNif;
		dto.settlement = cr.settlement;
		dto.state = cr.state;
		dto.numPayrolls = numPayrolls;
		
		return dto;
	}
}
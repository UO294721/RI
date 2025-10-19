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
	public static ContractRecord toRecord(ContractDto dto) {
		ContractRecord cr = new ContractRecord();
		
		cr.id = dto.id != null ? dto.id : UUID.randomUUID().toString();
		cr.version = dto.version > 0 ? dto.version : 1L;
		
		cr.mechanicId = dto.mechanic.id;
		cr.contractTypeId = dto.contractType.id;
		cr.professionalGroupId = dto.professionalGroup.id;
		
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
		dto.mechanic = toMechanicDto(mr);
		
		// Contract type info
		dto.contractType = toContractTypeDto(ctr);
		
		// Professional group info
		dto.professionalGroup = toProfessionalGroupDto(pgr);
		
		return dto;
	}
	
	public static ContractDto toDto(ContractRecord cr, MechanicOfContractDto mr,
	                                ContractTypeOfContractDto ctr,
	                                ProfessionalGroupOfContractDto pgr) {
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
		dto.mechanic = mr;
		
		// Contract type info
		dto.contractType = ctr;
		
		// Professional group info
		dto.professionalGroup = pgr;
		
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
	
	public static MechanicOfContractDto toMechanicDto(MechanicRecord mr) {
		MechanicOfContractDto dto = new MechanicOfContractDto();
		
		dto.id = mr.id;
		dto.nif = mr.nif;
		dto.name = mr.name;
		dto.surname = mr.surname;
		return dto;
	}
	
	public static ContractTypeOfContractDto toContractTypeDto(ContractTypeRecord ctr) {
		ContractTypeOfContractDto dto = new ContractTypeOfContractDto();
		
		dto.id = ctr.id;
		dto.name = ctr.name;
		dto.compensationDaysPerYear = ctr.compensationDays;
		return dto;
	}
	
	public static ProfessionalGroupOfContractDto toProfessionalGroupDto(ProfessionalGroupRecord pgr) {
		ProfessionalGroupOfContractDto dto = new ProfessionalGroupOfContractDto();
		
		dto.id = pgr.id;
		dto.name = pgr.name;
		dto.trieniumPayment = pgr.trienniumPayment;
		dto.productivityRate = pgr.productivityRate;
		return dto;
	}
}
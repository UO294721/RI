package uo.ri.cws.application.service.contract;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.*;

public class ContractDtoAssembler {
	
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
package uo.ri.cws.application.persistence.contract;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractRecordAssembler {
	
	public static ContractRecord toRecord(ResultSet rs) throws SQLException {
		ContractRecord cr = new ContractRecord();
		
		cr.id = rs.getString("id");
		cr.mechanicId = rs.getString("mechanic_id");
		cr.contractTypeId = rs.getString("contracttype_id");
		cr.professionalGroupId = rs.getString("professionalgroup_id");
		cr.startDate = rs.getDate("startdate").toLocalDate();
		
		if (rs.getDate("enddate") != null) {
			cr.endDate = rs.getDate("enddate").toLocalDate();
		}
		
		cr.annualBaseSalary = rs.getDouble("annualbasesalary");
		cr.taxRate = rs.getDouble("taxrate");
		cr.settlement = rs.getDouble("settlement");
		cr.state = rs.getString("state");
		cr.version = rs.getLong("version");
		cr.createdAt = rs.getTimestamp("createdat").toLocalDateTime();
		cr.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime();
		cr.entityState = rs.getString("entitystate");
		
		return cr;
	}
}
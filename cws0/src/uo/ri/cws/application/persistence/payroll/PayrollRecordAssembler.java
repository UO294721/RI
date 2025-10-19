package uo.ri.cws.application.persistence.payroll;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PayrollRecordAssembler {
	
	public static PayrollRecord toRecord(ResultSet rs) throws SQLException {
		PayrollRecord pr = new PayrollRecord();
		
		pr.id = rs.getString("id");
		pr.version = rs.getLong("version");
		pr.contractId = rs.getString("contract_id");
		pr.date = rs.getDate("date").toLocalDate();
		
		// Earnings
		pr.baseSalary = rs.getDouble("basesalary");
		pr.extraSalary = rs.getDouble("extrasalary");
		pr.productivityEarning = rs.getDouble("productivityearning");
		pr.trienniumEarning = rs.getDouble("trienniumearning");
		
		// Deductions
		pr.taxDeduction = rs.getDouble("taxdeduction");
		pr.nicDeduction = rs.getDouble("nicdeduction");
		
		pr.createdAt = rs.getTimestamp("createdat").toLocalDateTime();
		pr.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime();
		pr.entityState = rs.getString("entitystate");
		
		return pr;
	}
}
package uo.ri.cws.application.persistence.payroll.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of PayrollGateway using JDBC.
 * Follows the Table Data Gateway pattern.
 */
public class PayrollGatewayImpl implements PayrollGateway {
	
	@Override
	public void add(PayrollRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_ADD"))) {
				
				pst.setString(1, t.id);
				pst.setString(2, t.contractId);
				pst.setDate(3, Date.valueOf(t.date));
				
				// Earnings
				pst.setDouble(4, t.baseSalary);
				pst.setDouble(5, t.extraSalary);
				pst.setDouble(6, t.productivityEarning);
				pst.setDouble(7, t.trienniumEarning);
				
				// Deductions
				pst.setDouble(8, t.taxDeduction);
				pst.setDouble(9, t.nicDeduction);
				
				
				pst.setLong(10, t.version);
				pst.setTimestamp(11, Timestamp.valueOf(t.createdAt));
				pst.setTimestamp(12, Timestamp.valueOf(t.updatedAt));
				pst.setString(13, t.entityState);
				
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error adding payroll", e);
		}
	}
	
	@Override
	public void remove(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_DELETE"))) {
				pst.setString(1, id);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error removing payroll", e);
		}
	}
	
	@Override
	public void update(PayrollRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_UPDATE"))) {
				
				// Earnings
				pst.setDouble(1, t.baseSalary);
				pst.setDouble(2, t.extraSalary);
				pst.setDouble(3, t.productivityEarning);
				pst.setDouble(4, t.trienniumEarning);
				
				// Deductions
				pst.setDouble(5, t.taxDeduction);
				pst.setDouble(6, t.nicDeduction);
				
				
				pst.setTimestamp(10, Timestamp.valueOf(t.updatedAt));
				pst.setString(11, t.id);
				
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error updating payroll", e);
		}
	}
	
	@Override
	public Optional<PayrollRecord> findById(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_FINDBYID"))) {
				pst.setString(1, id);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return Optional.of(PayrollRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding payroll by id", e);
		}
		
		return Optional.empty();
	}
	
	@Override
	public List<PayrollRecord> findAll() throws PersistenceException {
		List<PayrollRecord> payrolls = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_FINDALL"))) {
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						payrolls.add(PayrollRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding all payrolls", e);
		}
		
		return payrolls;
	}
	
	@Override
	public List<PayrollRecord> findLast12ByContractId(String contractId)
			throws PersistenceException {
		List<PayrollRecord> payrolls = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_FINDLAST12_BY_CONTRACT"))) {
				pst.setString(1, contractId);
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						payrolls.add(PayrollRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding last 12 payrolls", e);
		}
		
		return payrolls;
	}
	
	@Override
	public List<PayrollRecord> findByContractId(String contractId)
			throws PersistenceException {
		List<PayrollRecord> payrolls = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_FINDBY_CONTRACT"))) {
				pst.setString(1, contractId);
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						payrolls.add(PayrollRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding payrolls by contract", e);
		}
		
		return payrolls;
	}
	
	@Override
	public List<PayrollRecord> findByDate(LocalDate date)
			throws PersistenceException {
		List<PayrollRecord> payrolls = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_FINDBY_DATE"))) {
				pst.setDate(1, Date.valueOf(date));
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						payrolls.add(PayrollRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding payrolls by date", e);
		}
		
		return payrolls;
	}
	
	@Override
	public List<PayrollRecord> findByContractIdAndDate(String contractId, LocalDate date)
			throws PersistenceException {
		List<PayrollRecord> payrolls = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_FINDBY_CONTRACT_AND_DATE"))) {
				pst.setString(1, contractId);
				pst.setDate(2, Date.valueOf(date));
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						payrolls.add(PayrollRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding payroll by contract and date", e);
		}
		
		return payrolls;
	}
}
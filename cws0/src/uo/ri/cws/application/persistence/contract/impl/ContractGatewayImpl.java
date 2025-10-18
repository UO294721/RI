package uo.ri.cws.application.persistence.contract.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContractGatewayImpl implements ContractGateway {
	
	@Override
	public void add(ContractRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_ADD"))) {
				pst.setString(1, t.id);
				pst.setString(2, t.mechanicId);
				pst.setString(3, t.contractTypeId);
				pst.setString(4, t.professionalGroupId);
				pst.setDate(5, Date.valueOf(t.startDate));
				if (t.endDate != null) {
					pst.setDate(6, Date.valueOf(t.endDate));
				} else {
					pst.setNull(6, Types.DATE);
				}
				pst.setDouble(7, t.annualBaseSalary);
				pst.setDouble(8, t.taxRate);
				pst.setDouble(9, t.settlement);
				pst.setString(10, t.state);
				pst.setLong(11, t.version);
				pst.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
				pst.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
				pst.setString(14, "ENABLED");
				
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public void remove(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_DELETE"))) {
				pst.setString(1, id);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public void update(ContractRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_UPDATE"))) {
				if (t.endDate != null) {
					pst.setDate(1, Date.valueOf(t.endDate));
				} else {
					pst.setNull(1, Types.DATE);
				}
				pst.setDouble(2, t.annualBaseSalary);
				pst.setDouble(3, t.settlement);
				pst.setString(4, t.state);
				pst.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				pst.setString(6, t.id);
				
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public Optional<ContractRecord> findById(String id) throws PersistenceException {
		Optional<ContractRecord> result = Optional.empty();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_FINDBYID"))) {
				pst.setString(1, id);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						result = Optional.of(ContractRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return result;
	}
	
	@Override
	public List<ContractRecord> findAll() throws PersistenceException {
		List<ContractRecord> contracts = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_FINDALL"))) {
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						contracts.add(ContractRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return contracts;
	}
	
	@Override
	public Optional<ContractRecord> findInforceByMechanicId(String mechanicId)
			throws PersistenceException {
		Optional<ContractRecord> result = Optional.empty();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_FINDINFORCE_BY_MECHANIC"))) {
				pst.setString(1, mechanicId);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						result = Optional.of(ContractRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return result;
	}
	
	@Override
	public List<ContractRecord> findByMechanicId(String mechanicId)
			throws PersistenceException {
		List<ContractRecord> contracts = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_FINDBYMECHANIC"))) {
				pst.setString(1, mechanicId);
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						contracts.add(ContractRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return contracts;
	}
	
	@Override
	public List<ContractRecord> findInforceContracts() throws PersistenceException {
		List<ContractRecord> contracts = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_FINDINFORCE"))) {
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						contracts.add(ContractRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return contracts;
	}
	
	@Override
	public Optional<ContractRecord> findByType(String name) throws PersistenceException {
		Optional<ContractRecord> result = Optional.empty();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTS_FINDBYTYPE"))) {
				pst.setString(1, name);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						result = Optional.of(ContractRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return result;
	}
	
	@Override
	public int countPayrollsByContractId(String contractId)
			throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPAYROLLS_COUNT_BY_CONTRACT"))) {
				pst.setString(1, contractId);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return 0;
	}
}

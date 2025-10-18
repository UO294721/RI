package uo.ri.cws.application.persistence.contracttype.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContractTypeGatewayImpl implements ContractTypeGateway {
	
	@Override
	public void add(ContractTypeRecord record) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTTYPES_ADD"))) {
				pst.setString(1, record.id);
				pst.setString(2, record.name);
				pst.setDouble(3, record.compensationDays);
				pst.setLong(4, record.version);
				pst.setTimestamp(5, Timestamp.valueOf(record.createdAt));
				pst.setTimestamp(6, Timestamp.valueOf(record.updatedAt));
				pst.setString(7, record.entityState);
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
					Queries.getSQLSentence("TCONTRACTTYPES_DELETE"))) {
				pst.setString(1, id);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public void update(ContractTypeRecord record) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTTYPES_UPDATE"))) {
				pst.setDouble(1, record.compensationDays);
				pst.setTimestamp(2, Timestamp.valueOf(record.updatedAt));
				pst.setString(3, record.id);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public Optional<ContractTypeRecord> findById(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTTYPES_FINDBYID"))) {
				pst.setString(1, id);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return Optional.of(toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return Optional.empty();
	}
	
	@Override
	public List<ContractTypeRecord> findAll() throws PersistenceException {
		List<ContractTypeRecord> types = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTTYPES_FINDALL"))) {
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						types.add(toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return types;
	}
	
	private ContractTypeRecord toRecord(ResultSet rs) throws SQLException {
		ContractTypeRecord ctr = new ContractTypeRecord();
		ctr.id = rs.getString("id");
		ctr.name = rs.getString("name");
		ctr.compensationDays = rs.getDouble("compensationdaysperyear");
		ctr.version = rs.getLong("version");
		ctr.createdAt = rs.getTimestamp("createdat").toLocalDateTime();
		ctr.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime();
		ctr.entityState = rs.getString("entitystate");
		return ctr;
	}
	
	@Override
	public Optional<ContractTypeRecord> findByName(String name) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCONTRACTTYPES_FINDBYNAME"))) {
				pst.setString(1, name);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return Optional.of(toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return Optional.empty();
	}
}


package uo.ri.cws.application.persistence.professionalgroup.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {
	
	@Override
	public void add(ProfessionalGroupRecord t) throws PersistenceException {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public void remove(String id) throws PersistenceException {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public void update(ProfessionalGroupRecord t) throws PersistenceException {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public Optional<ProfessionalGroupRecord> findById(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYID"))) {
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
	public Optional<ProfessionalGroupRecord> findByName(String name) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYNAME"))) {
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
	
	@Override
	public List<ProfessionalGroupRecord> findAll() throws PersistenceException {
		List<ProfessionalGroupRecord> groups = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDALL"))) {
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						groups.add(toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return groups;
	}
	
	private ProfessionalGroupRecord toRecord(ResultSet rs) throws SQLException {
		ProfessionalGroupRecord pgr = new ProfessionalGroupRecord();
		pgr.id = rs.getString("id");
		pgr.name = rs.getString("name");
		pgr.trienniumPayment = rs.getDouble("trienniumpayment");
		pgr.productivityRate = rs.getDouble("productivityrate");
		pgr.version = rs.getLong("version");
		pgr.createdAt = rs.getTimestamp("createdat").toLocalDateTime();
		pgr.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime();
		pgr.entityState = rs.getString("entitystate");
		return pgr;
	}
}
package uo.ri.cws.application.persistence.client.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ClientGateway using JDBC.
 * Follows the Table Data Gateway pattern.
 */
public class ClientGatewayImpl implements ClientGateway {
	
	@Override
	public void add(ClientRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCLIENTS_ADD"))) {
				
				pst.setString(1, t.id);
				pst.setString(2, t.nif);
				pst.setString(3, t.name);
				pst.setString(4, t.surname);
				pst.setString(5, t.addressStreet);
				pst.setString(6, t.addressCity);
				pst.setString(7, t.addressZipcode);
				pst.setString(8, t.phone);
				pst.setString(9, t.email);
				pst.setLong(10, t.version);
				pst.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
				pst.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
				pst.setString(13, "ENABLED");
				
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error adding client", e);
		}
	}
	
	@Override
	public void remove(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCLIENTS_DELETE"))) {
				pst.setString(1, id);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error removing client", e);
		}
	}
	
	@Override
	public void update(ClientRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCLIENTS_UPDATE"))) {
				
				pst.setString(1, t.name);
				pst.setString(2, t.surname);
				pst.setString(3, t.addressStreet);
				pst.setString(4, t.addressCity);
				pst.setString(5, t.addressZipcode);
				pst.setString(6, t.phone);
				pst.setString(7, t.email);
				pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				pst.setString(9, t.id);
				
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error updating client", e);
		}
	}
	
	@Override
	public Optional<ClientRecord> findById(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCLIENTS_FINDBYID"))) {
				pst.setString(1, id);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return Optional.of(ClientRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding client by id", e);
		}
		
		return Optional.empty();
	}
	
	@Override
	public List<ClientRecord> findAll() throws PersistenceException {
		List<ClientRecord> clients = new ArrayList<>();
		
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCLIENTS_FINDALL"))) {
				
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						clients.add(ClientRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding all clients", e);
		}
		
		return clients;
	}
	
	@Override
	public Optional<ClientRecord> findByNif(String nif) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			
			try (PreparedStatement pst = c.prepareStatement(
					Queries.getSQLSentence("TCLIENTS_FINDBYNIF"))) {
				pst.setString(1, nif);
				
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return Optional.of(ClientRecordAssembler.toRecord(rs));
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException("Error finding client by nif", e);
		}
		
		return Optional.empty();
	}
	
}
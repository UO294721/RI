package uo.ri.cws.application.persistence.vehicle.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleGatewayImpl implements VehicleGateway {
	@Override
	public void add(VehicleRecord vehicleRecord) throws PersistenceException {
		
		try{
			
			Connection c = Jdbc.getCurrentConnection();
			
			try(PreparedStatement ps =
					    c.prepareStatement(Queries.getSQLSentence(
								"TVEHICLES_ADD"))) {
				
				ps.setString(1, vehicleRecord.id);
				ps.setString(2, vehicleRecord.clientId);
				ps.setString(3, vehicleRecord.vehicleTypeId);
				ps.setString(4, vehicleRecord.plate);
				ps.setString(5, vehicleRecord.make);
				ps.setString(6, vehicleRecord.model);
				ps.setLong(7, vehicleRecord.version);
				ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
				ps.setString(10, "ENABLED");
				
				ps.executeUpdate();
				
			}
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		}
		
	}
	
	@Override
	public void remove(String id) throws PersistenceException {
		try{
			
			Connection c = Jdbc.getCurrentConnection();
			
			try(PreparedStatement ps =
					    c.prepareStatement(Queries.getSQLSentence(
							    "TVEHICLES_DELETE"))) {
				ps.setString(1, id);
				ps.executeUpdate();
				
			}
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public void update(VehicleRecord vehicleRecord)
			throws PersistenceException {
		try{
			
			Connection c = Jdbc.getCurrentConnection();
			
			try(PreparedStatement ps =
					    c.prepareStatement(Queries.getSQLSentence(
							    "TVEHICLES_UPDATE"))) {
				
				ps.setString(1, vehicleRecord.plate);
				ps.setString(2, vehicleRecord.make);
				ps.setString(3, vehicleRecord.model);
				ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				ps.setString(6, vehicleRecord.id);
				
				ps.executeUpdate();
				
				
			}
			
		} catch(SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public Optional<VehicleRecord> findById(String id)
			throws PersistenceException {
		try{
			Connection c = Jdbc.getCurrentConnection();
			
			try(PreparedStatement ps = c.prepareStatement(
					Queries.getSQLSentence("TVEHICLES_FINDBYID"))) {
				ps.setString(1, id);
				
				try(ResultSet rs = ps.executeQuery()) {
					if(rs.next()) {
						return Optional.of(VehicleRecordAssembler.toRecord(rs));
					}
				}
				
			}
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return Optional.empty();
	}
	
	@Override
	public List<VehicleRecord> findAll() throws PersistenceException {
		
		try{
			Connection c = Jdbc.getCurrentConnection();
			
			try(PreparedStatement ps =
					    c.prepareStatement(Queries.getSQLSentence(
								"TVEHICLES_FINDALL"))) {
				try(ResultSet rs = ps.executeQuery()) {
					List<VehicleRecord> vehicles = new ArrayList<>();
					while(rs.next()) {
						vehicles.add(VehicleRecordAssembler.toRecord(rs));
					}
					return vehicles;
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
	}
	
	@Override
	public Optional<VehicleRecord> findByPlate(String plate) {
		
		try{
			Connection c = Jdbc.getCurrentConnection();
			
			try(PreparedStatement ps = c.prepareStatement(
					Queries.getSQLSentence("TVEHICLES_FINDBYPLATE"))) {
				ps.setString(1, plate);
				
				try(ResultSet rs = ps.executeQuery()) {
					if(rs.next()) {
						return Optional.of(VehicleRecordAssembler.toRecord(rs));
					}
				}
				
			}
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return Optional.empty();
	}
	
	@Override
	public List<VehicleRecord> findByClientId(String clientId) {
		
		List<VehicleRecord> vehicles = List.of();
		
		try{
			Connection c = Jdbc.getCurrentConnection();
			
			try(PreparedStatement ps =
					    c.prepareStatement(Queries.getSQLSentence(
								"TVEHICLES_FINDBYCLIENTID"))) {
				
				ps.setString(1, clientId);
				
				try(ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						vehicles.add(VehicleRecordAssembler.toRecord(rs));
					}
				}
				
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return vehicles;
		
	}
}

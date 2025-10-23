package uo.ri.cws.application.persistence.vehicle.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class VehicleGatewayImpl implements VehicleGateway {
	@Override
	public void add(VehicleRecord vehicleRecord) throws PersistenceException {
	
	}
	
	@Override
	public void remove(String id) throws PersistenceException {
	
	}
	
	@Override
	public void update(VehicleRecord vehicleRecord)
			throws PersistenceException {
		
	}
	
	@Override
	public Optional<VehicleRecord> findById(String id)
			throws PersistenceException {
		return Optional.empty();
	}
	
	@Override
	public List<VehicleRecord> findAll() throws PersistenceException {
		return List.of();
	}
	
	@Override
	public Optional<VehicleRecord> findByPlate(String plate) {
		
		try{
			Connection c = Jdbc.getCurrentConnection();
			
			try(PreparedStatement ps = c.prepareStatement(
					Queries.getSQLSentence("TVEHICLES_FINDBYPLATE"))) {
				ps.setString(1, plate);
				
				try(ResultSet rs = ps.executeQuery()) {
				
				}
				
			}
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return Optional.empty();
	}
}

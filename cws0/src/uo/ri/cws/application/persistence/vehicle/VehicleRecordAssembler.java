package uo.ri.cws.application.persistence.vehicle;

import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleRecordAssembler {
	
	public static VehicleRecord toRecord(ResultSet rs) throws SQLException {
		VehicleRecord vr = new VehicleRecord();
		vr.id = rs.getString("id");
		vr.clientId = rs.getString("client_id");
		vr.plate = rs.getString("platenumber");
		vr.make = rs.getString("make");
		vr.model = rs.getString("model");
		vr.vehicleTypeId = rs.getString("vehicletype_id");
		vr.version = rs.getLong("version");
		vr.createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
		vr.updatedAt = rs.getTimestamp("updatedAt").toLocalDateTime();
		vr.entityState = rs.getString("entitystate");
		return vr;
	}
	
	public static VehicleRecord toRecord(VehicleDto dto) {
		VehicleRecord vr = new VehicleRecord();
		
		vr.id = dto.id;
		vr.clientId = dto.clientId;
		vr.plate = dto.plate;
		vr.make = dto.make;
		vr.model = dto.model;
		vr.vehicleTypeId = dto.vehicleTypeId;
		vr.version = dto.version;
		return vr;
	}
	
	
	
}

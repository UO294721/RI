package uo.ri.cws.application.persistence.vehicle;

import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;

import java.sql.ResultSet;

public class VehicleRecordAssembler {
	
	public static VehicleRecord toRecord(ResultSet rs) {
		VehicleRecord vr = new VehicleRecord();
		
		return vr;
	}
	
}

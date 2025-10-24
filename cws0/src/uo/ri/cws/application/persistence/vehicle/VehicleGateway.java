package uo.ri.cws.application.persistence.vehicle;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VehicleGateway extends Gateway<VehicleRecord> {
	
	public Optional<VehicleRecord> findByPlate(String plate);
	
	public List<VehicleRecord> findByClientId(String clientId);
	
	public class VehicleRecord {
		public String id;
		public long version;
		
		public String plate;
		public String make;
		public String model;
		
		public String clientId;
		public String vehicleTypeId;
		
		public LocalDateTime createdAt;
		public LocalDateTime updatedAt;
		public String entityState;
	}
	
}

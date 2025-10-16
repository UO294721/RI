package uo.ri.cws.application.persistence.mechanic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicRecordAssembler {
	
	public static MechanicRecord toRecord(ResultSet rs) throws SQLException{
		
		MechanicRecord mr = new MechanicRecord();
		mr.id = rs.getString("id");
		mr.nif = rs.getString("nif");
		mr.name = rs.getString("name");
		mr.surname = rs.getString("surname");
		mr.version = rs.getLong("version");
		mr.createdAt = rs.getTimestamp(6).toLocalDateTime();
		mr.updatedAt = rs.getTimestamp(7).toLocalDateTime();
		mr.entityState = rs.getString(8);
		return mr;
		
	}
	
	public static MechanicRecord toRecord(MechanicDto dto ) {
		MechanicRecord mr = new MechanicRecord();
		
		mr.id = dto.id;
		mr.nif = dto.nif;
		mr.name = dto.name;
		mr.surname = dto.surname;
		mr.version = dto.version;
		mr.createdAt = LocalDateTime.now();
		mr.updatedAt = LocalDateTime.now();
		mr.entityState = "ENABLED";
		return mr;
	}

}

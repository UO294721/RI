package uo.ri.cws.application.persistence.client;

import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ClientRecordAssembler {
	
	public static ClientRecord toRecord(ResultSet rs) throws SQLException {
		ClientRecord cr = new ClientRecord();
		
		cr.id = rs.getString("id");
		cr.nif = rs.getString("nif");
		cr.name = rs.getString("name");
		cr.surname = rs.getString("surname");
		cr.addressStreet = rs.getString("street");
		cr.addressCity = rs.getString("city");
		cr.addressZipcode = rs.getString("zipcode");
		cr.phone = rs.getString("phone");
		cr.email = rs.getString("email");
		cr.version = rs.getLong("version");
		cr.createdAt = rs.getTimestamp("createdat").toLocalDateTime();
		cr.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime();
		cr.entityState = rs.getString("entitystate");
		
		return cr;
	}
	
	public static ClientDto toDto(ClientRecord cr) {
		ClientDto dto = new ClientDto();
		
		dto.id = cr.id;
		dto.nif = cr.nif;
		dto.name = cr.name;
		dto.surname = cr.surname;
		dto.addressStreet = cr.addressStreet;
		dto.addressCity = cr.addressCity;
		dto.addressZipcode = cr.addressZipcode;
		dto.phone = cr.phone;
		dto.email = cr.email;
		dto.version = cr.version;
		
		return dto;
	}
	
	public static ClientRecord toRecord(ClientDto dto) {
		ClientRecord cr = new ClientRecord();
		cr.id = dto.id;
		cr.nif = dto.nif;
		cr.name = dto.name;
		cr.surname = dto.surname;
		cr.addressStreet = dto.addressStreet;
		cr.addressCity = dto.addressCity;
		cr.addressZipcode = dto.addressZipcode;
		cr.phone = dto.phone;
		cr.email = dto.email;
		cr.version = dto.version;
		cr.createdAt = LocalDateTime.now();
		cr.updatedAt = LocalDateTime.now();
		cr.entityState = "ENABLED";
		return cr;
	}
}
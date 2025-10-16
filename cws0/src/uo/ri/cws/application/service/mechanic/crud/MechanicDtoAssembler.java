package uo.ri.cws.application.service.mechanic.crud;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicDtoAssembler {
	
	public static MechanicRecord toRecord(MechanicDto dto) {
		MechanicRecord mr = new MechanicRecord();
		mr.id = dto.id;
		mr.name = dto.name;
		mr.surname = dto.surname;
		mr.version = dto.version;
		return mr;
	}

    public static MechanicDto toDto(MechanicRecord mr) {
        MechanicDto mdto = new MechanicDto();
        mdto.id = mr.id;
        mdto.nif = mr.nif;
        mdto.name = mr.name;
        mdto.surname = mr.surname;
        mdto.version = mr.version;
        return mdto;
    }
	
}

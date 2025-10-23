package uo.ri.cws.application.service.vehicle.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class UpdateVehicle implements Command<Void> {
	
	private final VehicleGateway vg = Factories.persistence.forVehicle();
	private final VehicleDto dto;
	
	public UpdateVehicle( VehicleDto dto ) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.id);
		this.dto = dto;
	}
	
	@Override
	public Void execute() throws BusinessException {
		
		try{
			
			Optional<VehicleRecord> ovr = vg.findById(dto.id);
			BusinessChecks.exists(ovr);
			VehicleRecord vr = ovr.get();
			BusinessChecks.hasVersion(dto.version, vr.version);
			
			
			
		} catch(PersistenceException e) {
			throw new BusinessException("Error updating the vehicle");
		}
		
		return null;
	}
}

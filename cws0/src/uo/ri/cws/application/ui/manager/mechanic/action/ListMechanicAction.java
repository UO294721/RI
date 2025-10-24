package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class ListMechanicAction implements Action {

    

    @Override
    public void execute() throws BusinessException {

        // Get info
        String nif = Console.readString("nif");
		
        MechanicCrudService mcs = Factories.service.forMechanicCrudService();
        
        Optional<MechanicDto> odto = mcs.findById(nif);
		if(!odto.isPresent()) {
			Console.println("Mechanic not found");
			return;
		}
		
		MechanicDto dto = odto.get();
        
        Printer.printMechanic(dto);
    }
}
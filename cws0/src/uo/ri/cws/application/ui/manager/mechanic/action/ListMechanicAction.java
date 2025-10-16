package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {

    

    @Override
    public void execute() throws BusinessException {

        // Get info
        String nif = Console.readString("nif");

        Console.println("\nMechanic information \n");
        
        MechanicCrudService mcs = new MechanicCrudServiceImpl();
        
        MechanicDto dto = mcs.findByNif(nif).get();
        
        Console.printf("\t%s %s %s %s %d\n",
                dto.id, dto.name, dto.surname, dto.nif, dto.version);
        
    }
}
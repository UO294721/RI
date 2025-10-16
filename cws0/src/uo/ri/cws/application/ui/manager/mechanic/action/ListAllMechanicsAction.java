package uo.ri.cws.application.ui.manager.mechanic.action;


import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListAllMechanicsAction implements Action {

    @Override
    public void execute() throws BusinessException {

        Console.println("\nList of mechanics \n");

        MechanicCrudService mcs = new MechanicCrudServiceImpl();
        mcs.findAll().forEach(i -> Console.printf("\t%s %s %s %s %d\n",
                i.id,
                i.nif,
                i.name,
                i.surname,
                i.version));
        
    }
}
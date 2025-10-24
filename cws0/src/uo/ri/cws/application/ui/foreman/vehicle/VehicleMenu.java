package uo.ri.cws.application.ui.foreman.vehicle;

import uo.ri.cws.application.ui.foreman.vehicle.action.AddVehicleAction;
import uo.ri.cws.application.ui.foreman.vehicle.action.DeleteVehicleAction;
import uo.ri.cws.application.ui.foreman.vehicle.action.ListAllVehiclesAction;
import uo.ri.cws.application.ui.foreman.vehicle.action.UpdateVehicleAction;
import uo.ri.util.menu.BaseMenu;

public class VehicleMenu extends BaseMenu {

    public VehicleMenu() {
        menuOptions = new Object[][] { { "Foreman > Vehicle management", null },

                { "Register new vehicle", AddVehicleAction.class },
                { "Update vehicle", UpdateVehicleAction.class },
                { "Disable vehicle", DeleteVehicleAction.class },
                { "List all vehicles", ListAllVehiclesAction.class }, };
    }

}

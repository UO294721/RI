package uo.ri.cws.application.ui.foreman.client;

import uo.ri.cws.application.ui.foreman.client.action.AddClientAction;
import uo.ri.cws.application.ui.foreman.client.action.DeleteClientAction;
import uo.ri.cws.application.ui.foreman.client.action.ListAllClientsAction;
import uo.ri.cws.application.ui.foreman.client.action.UpdateClientAction;
import uo.ri.util.menu.BaseMenu;

public class ClientMenu extends BaseMenu {

    public ClientMenu() {
        menuOptions = new Object[][] { { "Foreman > Client management", null },

                { "Register a client", AddClientAction.class },
                { "Update a client", UpdateClientAction.class },
                { "Disable a client", DeleteClientAction.class },
                { "List all clients", ListAllClientsAction.class }, };
    }

}

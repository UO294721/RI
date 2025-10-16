package uo.ri.cws.application.ui.cashier.action;

import java.util.ArrayList;
import java.util.List;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class InvoiceWorkorderAction implements Action {
    
    @Override
    public void execute() throws BusinessException {
        List<String> workOrderIds = new ArrayList<>();

        // Ask the user the work order ids
        do {
            String id = Console.readString("Workorder id");
            workOrderIds.add(id);
        } while (moreWorkOrders());
        
        InvoicingService is = Factories.service.forCreateInvoiceService();
        displayInvoice(is.create(workOrderIds));
        
        
    }

    private boolean moreWorkOrders() {
        return Console.readString("more work orders? (y/n) ")
                .equalsIgnoreCase("y");
    }

    private void displayInvoice(InvoiceDto dto) {

        Console.printf("Invoice number: %d\n", dto.number);
        Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dto.date);
        Console.printf("\tAmount: %.2f €\n", dto.amount - dto.vat);
        Console.printf("\tVAT: %.1f %% \n", dto.vat);
        Console.printf("\tTotal (including VAT): %.2f €\n", dto.amount);
    }
}

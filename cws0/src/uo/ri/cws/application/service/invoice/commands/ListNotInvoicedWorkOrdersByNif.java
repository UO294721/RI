package uo.ri.cws.application.service.invoice.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;

public class ListNotInvoicedWorkOrdersByNif implements Command<List<InvoicingWorkOrderDto>> {
	
	private String nif;
    private InvoiceWorkOrderGateway ig = Factories.persistence.forInvoice();

	public ListNotInvoicedWorkOrdersByNif(String nif) {
		ArgumentChecks.isNotNull(nif);
		this.nif = nif;
	}
	
	public List<InvoicingWorkOrderDto> execute() {

        return ig.findNotInvoicedByClientNif(nif)
                .stream()
                .map(InvoiceRecordAssembler::toDto)
                .toList();

	}

}

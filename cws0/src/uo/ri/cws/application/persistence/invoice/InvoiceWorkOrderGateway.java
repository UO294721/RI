package uo.ri.cws.application.persistence.invoice;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceWorkOrderGateway.InvoiceRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceWorkOrderGateway extends Gateway<InvoiceRecord> {

    class InvoiceRecord{
        public String id;		// the surrogate id (UUID)
        public long version;

        public double amount;	// total amount (money) vat included
        public double vat;		// amount of vat (money)
        public long number;		// the invoice identity, a sequential number
        public LocalDate date;	// of the invoice
        public String state;	// the state as in InvoiceState
        public List<String> workOrderIds;

        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;
    }

    class InvoicingWorkOrderRecord {
        public String id;
        public String description;
        public LocalDateTime date;
        public String state;
        public double amount;
    }
	
	/**
	 * Finds all work orders that have not been invoiced yet by a client
	 * @param nif the client's nif
	 * @return List of work orders, may be empty
	 */
    List<InvoicingWorkOrderRecord> findNotInvoicedByClientNif(String nif);
	
	/**
	 * Finds the maximum invoice number currently in the database
	 * @return the maximum invoice number, or 0 if no invoices exist
	 * @throws PersistenceException on database errors
	 */
	long findMaxInvoiceNumber() throws PersistenceException;

}

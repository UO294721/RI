package uo.ri.cws.application.persistence.invoice.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceWorkOrderWorkOrderGatewayImpl implements InvoiceWorkOrderGateway {

    @Override
    public void add(InvoiceRecord ir) throws PersistenceException {

        Connection connection = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = connection
                .prepareStatement(Queries.getSQLSentence("TINVOICES_ADD"))) {
            ir.number = generateInvoiceNumber();
            pst.setString(1, ir.id);
            pst.setLong(2, ir.number);
            pst.setDate(3, Date.valueOf(ir.date));
            pst.setDouble(4, ir.vat);
            pst.setDouble(5, ir.amount);
            pst.setString(6, ir.state);
            pst.setLong(7, ir.version);
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            pst.setString(10, "ENABLED");
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private long generateInvoiceNumber() throws SQLException {
        Connection connection = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = connection
                .prepareStatement(Queries.getSQLSentence(
                        "TINVOICES_FINDNEXTNUMBER"))) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1) + 1;
                }
            }
        }
        return 1L; // Si no hay facturas previas, empezamos desde 1
    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(InvoiceRecord invoiceRecord) throws PersistenceException {

    }

    @Override
    public Optional<InvoiceRecord> findById(String id) throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
        return List.of();
    }

    @Override
    public List<InvoicingWorkOrderRecord> findNotInvoicedByClientNif(String nif) {

        List<InvoicingWorkOrderRecord> notInvoicedWorkOrders =
                new ArrayList<>();

        try {

            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence(
                            "TWORKORDERS_FINDNOTINVOICED"))) {
                pst.setString(1, nif);
                try (ResultSet rs = pst.executeQuery();) {
                    while (rs.next()) {
                        notInvoicedWorkOrders.add(InvoiceRecordAssembler.toRecord(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return notInvoicedWorkOrders;
    }



}

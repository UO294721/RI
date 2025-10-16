package uo.ri.cws.application.persistence.workorder.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class WorkOrderGatewayImpl implements WorkOrderGateway {
    @Override
    public void add(WorkOrderRecord workOrderRecord) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(WorkOrderRecord workOrderRecord) throws PersistenceException {

        try{
            Connection c = Jdbc.getCurrentConnection();

            try(PreparedStatement pst =
                        c.prepareStatement(Queries.getSQLSentence(
                                "TWORKORDERS_UPDATE"))){
                pst.setString(1, workOrderRecord.invoiceId);
                pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                pst.setString(3, workOrderRecord.id);
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public Optional<WorkOrderRecord> findById(String id) throws PersistenceException {

        Optional<WorkOrderRecord> wo = Optional.empty();

        try{
            Connection c = Jdbc.getCurrentConnection();

            try(PreparedStatement pst =
                        c.prepareStatement(Queries.getSQLSentence(
                                "TWORKORDERS_FINDID"))){
                pst.setString(1, id);
                try(ResultSet rs = pst.executeQuery()){
                    if(rs.next()){
                        WorkOrderRecord record = WorkOrderRecordAssembler.toRecord(rs);
                        if(!record.state.equalsIgnoreCase("FINISHED"))
                            throw new PersistenceException("The workorder is not finished");
                        wo = Optional.of(record);
                    }
                    return wo;
                }
            }

        } catch(SQLException e){
            throw new PersistenceException(e);
        }

    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {
        return List.of();
    }
}

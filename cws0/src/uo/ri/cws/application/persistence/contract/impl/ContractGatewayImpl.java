package uo.ri.cws.application.persistence.contract.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ContractGatewayImpl implements ContractGateway {

    @Override
    public boolean hasInForceContract(String mechanicId) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();

            try(PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("IN_FORCE_CONTRACT"))){
                pst.setString(1, mechanicId);
                try(ResultSet rs = pst.executeQuery()){
                    return rs.next(); // Returns true if at least one in-force contract exists
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean hasTerminatedContract(String mechanicId) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();

            try(PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TERMINATED_CONTRACT"))){
                pst.setString(1, mechanicId);
                try(ResultSet rs = pst.executeQuery()){
                    return rs.next(); // Returns true if at least one terminated contract exists
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void add(ContractRecord contractRecord) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(ContractRecord contractRecord) throws PersistenceException {

    }

    @Override
    public Optional<ContractRecord> findById(String id) throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<ContractRecord> findAll() throws PersistenceException {
        return List.of();
    }
}

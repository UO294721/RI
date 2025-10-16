package uo.ri.cws.application.persistence.contracttype.impl;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContractTypeGatewayImpl implements ContractTypeGateway {

    @Override
    public void add(ContractTypeRecord record) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TCONTRACTTYPES_ADD"))) {
                pst.setString(1, record.id);
                pst.setString(2, record.name);
                pst.setDouble(3, record.compensationDaysPerYear);
                pst.setLong(4, record.version);
                pst.setTimestamp(5, Timestamp.valueOf(record.createdAt));
                pst.setTimestamp(6, Timestamp.valueOf(record.updatedAt));
                pst.setString(7, record.entityState);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TCONTRACTTYPES_DELETE"))) {
                pst.setString(1, id);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(ContractTypeRecord record) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TCONTRACTTYPES_UPDATE"))) {
                pst.setDouble(1, record.compensationDaysPerYear);
                pst.setTimestamp(2, Timestamp.valueOf(record.updatedAt));
                pst.setString(3, record.id);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<ContractTypeRecord> findById(String id) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TCONTRACTTYPES_FINDBYID"))) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSet(rs));
                    }
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<ContractTypeRecord> findByName(String name) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TCONTRACTTYPES_FINDBYNAME"))) {
                pst.setString(1, name);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSet(rs));
                    }
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractTypeRecord> findAll() throws PersistenceException {
        List<ContractTypeRecord> records = new ArrayList<>();
        try {
            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TCONTRACTTYPES_FINDALL"))) {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        records.add(mapResultSet(rs));
                    }
                    return records;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean hasContracts(String contractTypeId) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TCONTRACTTYPES_HAS_CONTRACTS"))) {
                pst.setString(1, contractTypeId);
                try (ResultSet rs = pst.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private ContractTypeRecord mapResultSet(ResultSet rs) throws SQLException {
        ContractTypeRecord record = new ContractTypeRecord();
        record.id = rs.getString("id");
        record.name = rs.getString("name");
        record.compensationDaysPerYear = rs.getDouble("compensationDaysPerYear");
        record.version = rs.getLong("version");
        record.createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
        record.updatedAt = rs.getTimestamp("updatedAt").toLocalDateTime();
        record.entityState = rs.getString("entityState");
        return record;
    }
}
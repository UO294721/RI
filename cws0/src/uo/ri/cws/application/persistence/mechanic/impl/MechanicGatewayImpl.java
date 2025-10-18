package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway{
	
	@Override
	public void add(MechanicRecord t) throws PersistenceException {
		
		try {

            Connection c = Jdbc.getCurrentConnection();

            try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_ADD"))) {
              pst.setString(1, t.id);
              pst.setString(2, t.nif);
              pst.setString(3, t.name);
              pst.setString(4, t.surname);
              pst.setLong(5, t.version);
              pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
              pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
              pst.setString(8, "ENABLED");

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
                    Queries.getSQLSentence("TMECHANICS_DELETE"))) {
                pst.setString(1, id);
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

	@Override
	public void update(MechanicRecord t) throws PersistenceException {
		
		// Process
        try {
        	
        	Connection c = Jdbc.getCurrentConnection();
        	
            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence("TMECHANICS_UPDATE"))) {
                pst.setString(1, t.name);
                pst.setString(2, t.surname);
                pst.setTimestamp(3, new Timestamp(
						System.currentTimeMillis()));
                pst.setString(4, t.id);

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
		
	}

	@Override
	public Optional<MechanicRecord> findById(String id) throws PersistenceException {
		
		Optional<MechanicRecord> m = Optional.empty();
		
		try{
			Connection c = Jdbc.getCurrentConnection();
			try(PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYID"))){
        		pst.setString(1, id);
        		try(ResultSet rs = pst.executeQuery()){
        			if(rs.next()) {
        				m = Optional.of(MechanicRecordAssembler.toRecord(rs));
        			}
        			return m;
        		}
        	} 
		} catch (SQLException e) {
            throw new PersistenceException(e);
        }
	}

	@Override
	public List<MechanicRecord> findAll() throws PersistenceException {

        ArrayList<MechanicRecord> mechanicsList =
                new ArrayList<>();
        try {
            Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence(
                            "TMECHANICS_FINDALL"))) {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        mechanicsList.add(MechanicRecordAssembler.toRecord(rs));
                    }
                    return mechanicsList;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

	}

	@Override
	public Optional<MechanicRecord> findByNif(String nif) {

        Optional<MechanicRecord> m = Optional.empty();

        try{
            Connection c = Jdbc.getCurrentConnection();
            try(PreparedStatement pst =
                        c.prepareStatement(Queries.getSQLSentence(
                                "TMECHANICS_FINDBYNIF"))){
                pst.setString(1, nif);
                try(ResultSet rs = pst.executeQuery()){
                    if(rs.next()) {
                        m = Optional.of(MechanicRecordAssembler.toRecord(rs));
                    }
                    return m;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

	}

}

package uo.ri.cws.application.persistence.intervention;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

import java.time.LocalDateTime;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    public class InterventionRecord {
        public String id;
        public long version;

        public String workorderId;
        public String mechanicId;
        public int minutes;

        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;
    }

    /**
     * Checks if a mechanic has any interventions
     * @param mechanicId the mechanic id
     * @return true if has interventions, false otherwise
     */
    boolean hasInterventions(String mechanicId) throws PersistenceException;

}

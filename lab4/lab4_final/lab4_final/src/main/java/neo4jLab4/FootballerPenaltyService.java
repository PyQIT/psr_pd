package neo4jLab4;

import org.neo4j.ogm.session.Session;

public class FootballerPenaltyService extends MethodService<FootballerPenalty> {
    public FootballerPenaltyService(Session session) {
        super(session);
    }

    @Override
    Class<FootballerPenalty> getEntityType() {
        return FootballerPenalty.class;
    }
}

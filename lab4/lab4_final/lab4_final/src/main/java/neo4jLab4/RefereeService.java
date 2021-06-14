package neo4jLab4;

import org.neo4j.ogm.session.Session;

public class RefereeService extends MethodService<Referee> {
    public RefereeService(Session session) {
        super(session);
    }

    @Override
    Class<Referee> getEntityType() {
        return Referee.class;
    }
}

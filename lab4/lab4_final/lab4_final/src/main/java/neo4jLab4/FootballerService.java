package neo4jLab4;

import org.neo4j.ogm.session.Session;

public class FootballerService extends MethodService<Footballer> {


    public FootballerService(Session session) {
        super(session);
    }

    @Override
    Class<Footballer> getEntityType() {
        return Footballer.class;
    }
}

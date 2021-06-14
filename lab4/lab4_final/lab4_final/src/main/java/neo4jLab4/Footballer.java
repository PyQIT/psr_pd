package neo4jLab4;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "neo4jLab4.Footballer")
@Data
public class Footballer {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;
    @Property("surname")
    private String surname;
    @Property("league")
    private String league;



    public Footballer(String name, String surname, String league) {
        this.name = name;
        this.surname = surname;
        this.league = league;

    }

    public Footballer() {
    }
}

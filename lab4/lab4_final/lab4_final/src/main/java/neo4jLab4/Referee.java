package neo4jLab4;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "neo4jLab4.Referee")
@Data
public class Referee {

    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "name")
    private String name;
    @Property(name = "surname")
    private String surname;
    @Property(name = "age")
    private Double age;

    public Referee() {
    }

    public Referee(String name, String surname, Double age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}

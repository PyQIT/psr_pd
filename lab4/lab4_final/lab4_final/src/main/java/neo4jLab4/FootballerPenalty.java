package neo4jLab4;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@NodeEntity(label = "neo4jLab4.FootballerPenalty")
@Data
public class FootballerPenalty {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "match")
    private String match;

    @Property(name = "penalty")
    private Double penalty;

    @Relationship(type = "referee")
    private Referee referee;

    @Relationship(type = "footballer")
    private Footballer footballer;

    public FootballerPenalty() {
    }

    public FootballerPenalty(String match, Double penalty, Referee referee, Footballer footballer) {
        this.match = match;
        this.penalty = penalty;
        this.referee = referee;
        this.footballer = footballer;
    }
}

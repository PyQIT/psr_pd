import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Match {
    private String id;
    private String partitionKey;
    private String place;
    private int ranking;
}

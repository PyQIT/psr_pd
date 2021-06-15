import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Footballer {
    private String id;
    private String partitionKey;
    private String name;
    private String surname;
}

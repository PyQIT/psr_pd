import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Club {
    private String id;
    private String partitionKey;
    private String name;
}

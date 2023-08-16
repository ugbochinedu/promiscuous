package africa.semicolon.promiscuous.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sender {
    private String name;
    @NonNull
    private String email;
}

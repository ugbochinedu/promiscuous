package africa.semicolon.promiscuous.dtos.request;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Recipient {
    private String name;

    @NonNull
    private String email;
}

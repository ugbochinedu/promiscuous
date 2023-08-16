package africa.semicolon.promiscuous.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class GetUserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String address;
    private String phoneNumber;
}

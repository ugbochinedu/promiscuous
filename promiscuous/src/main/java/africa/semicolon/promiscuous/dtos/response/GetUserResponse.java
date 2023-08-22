package africa.semicolon.promiscuous.dtos.response;

import lombok.*;

@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString

public class GetUserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String profileImage;
}

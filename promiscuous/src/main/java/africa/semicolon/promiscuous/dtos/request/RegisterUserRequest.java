package africa.semicolon.promiscuous.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserRequest {
    private String email;
    private String password;
}

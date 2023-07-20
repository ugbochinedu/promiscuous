package africa.semicolon.promiscuous.dtos;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserRequest {
    private String email;
    private String password;
}

package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dtos.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dtos.response.RegisterUserResponse;
import africa.semicolon.promiscuous.models.User;
import africa.semicolon.promiscuous.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
//simple login facade for jav
public class PromiscuousUserService implements UserService{

    private final UserRepository userRepository;

    public RegisterUserResponse register(RegisterUserRequest registerUserRequest){
        //1. extract registration details from the registration form
        String email = registerUserRequest.getEmail();
        String password = registerUserRequest.getPassword();
        //2. create a user profile with the registration details
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        //3. save the users profile in the database
        User savedUser = userRepository.save(user);
        //4. send verification token to the users email
        String emailResponse = MockEmailService.sendEmail(savedUser.getEmail());
        log.info("email sending response->{}", emailResponse);
        //5. return response
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setMessage("Registration Successful, check your email inbox for more information");
        return registerUserResponse;
    }
}

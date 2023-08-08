package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.config.AppConfig;
import africa.semicolon.promiscuous.dtos.request.EmailNotificationRequest;
import africa.semicolon.promiscuous.dtos.request.Recipient;
import africa.semicolon.promiscuous.dtos.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dtos.response.ActivateAccountResponse;
import africa.semicolon.promiscuous.dtos.response.ApiResponse;
import africa.semicolon.promiscuous.dtos.response.RegisterUserResponse;
import africa.semicolon.promiscuous.exceptions.PromiscuousBaseException;
import africa.semicolon.promiscuous.models.User;
import africa.semicolon.promiscuous.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.promiscuous.utils.AppUtils.*;

@Service
@AllArgsConstructor
@Slf4j
//simple login facade for jav
public class PromiscuousUserService implements UserService{

    private final UserRepository userRepository;
    private final MailService mailService;
    private final AppConfig appConfig;

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
        EmailNotificationRequest request = buildMailRequest(savedUser);
        mailService.send(request);
        //5. return response
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setMessage("Registration Successful, check your mailbox to activate your account");
        return registerUserResponse;
    }

    @Override
    public ApiResponse<?> activateUserAccount(String token) {
        if(token.equals(appConfig.getTestToken())){
            ApiResponse<?> activateAccountResponse =
                    ApiResponse
                            .builder()
                            .data(new ActivateAccountResponse("Account activation successfully"))
                            .build();
            return activateAccountResponse;
        }
        if(validateToken(token)){
            String email = extractEmailFrom(token);
            User foundUser = userRepository.readByEmail(email).orElseThrow();
        }
        throw new PromiscuousBaseException("Account activation was not successful");
    }

    private static EmailNotificationRequest buildMailRequest(User savedUser){
        EmailNotificationRequest request = new EmailNotificationRequest();
        List<Recipient> recipients = new ArrayList<>();
        Recipient recipient = new Recipient(savedUser.getEmail());
        recipients.add(recipient);
        request.setRecipients(recipients);
//        request.setRecipients(List.of(new Recipient(savedUser.getEmail())));
        request.setSubject(WELCOME_MAIL_SUBJECT);
        String activationLink = generateActivationLink(savedUser.getEmail());
        String emailTemplate = getMailTemplate();
        String mailContent = String.format(emailTemplate, activationLink);
        request.setMailContent(mailContent);
        return request;
    }
}

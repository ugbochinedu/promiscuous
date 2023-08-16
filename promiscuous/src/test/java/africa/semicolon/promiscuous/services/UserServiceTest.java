package africa.semicolon.promiscuous.services;


import africa.semicolon.promiscuous.dtos.request.LoginRequest;
import africa.semicolon.promiscuous.dtos.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dtos.response.ApiResponse;
import africa.semicolon.promiscuous.dtos.response.GetUserResponse;
import africa.semicolon.promiscuous.dtos.response.LoginResponse;
import africa.semicolon.promiscuous.dtos.response.RegisterUserResponse;
import africa.semicolon.promiscuous.exceptions.BadCredentialsException;
import africa.semicolon.promiscuous.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
@Sql(scripts={"/db/insert.sql"})

public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    public void setUp(){
        addressRepository.deleteAll();
    }

    @Test
    public void testThatUserCanRegister(){
        RegisterUserRequest  registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("rofime9859@royalka.com");
        registerUserRequest.setPassword("password");
        var registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);
        assertNotNull(registerUserResponse.getMessage());
    }
    @Test
    public void testActivateUserAccount(){
        ApiResponse<?> activateUserAccountResponse =
                userService.activateUserAccount("abc1234.erytuol.67t756");
        assertThat(activateUserAccountResponse).isNotNull();
    }

    @Test
    public void getUserByIdTest(){
        GetUserResponse response = userService.getUserById(500L);
        assertThat(response).isNotNull();
    }

    @Test
    public void getAllUsers(){
        List<GetUserResponse> users = userService.getAllUsers(1, 5);
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(5);
    }

    @Test
    public void testThatUsersCanLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@email.com");
        loginRequest.setPassword("password");

        LoginResponse response = userService.login(loginRequest);
        assertThat(response).isNotNull();

        String accessToken = response.getAccessToken();
        assertThat(accessToken).isNotNull();
    }
    @Test
    public void testThatExceptionIsThrownWhenUserAuthenticatesWithBadCredentials(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@email.com");
        loginRequest.setPassword("bad_password");

        assertThatThrownBy(()->userService.login(loginRequest))
                .isInstanceOf(BadCredentialsException.class);
    }


}
package africa.semicolon.promiscuous.services;


import africa.semicolon.promiscuous.dtos.request.LoginRequest;
import africa.semicolon.promiscuous.dtos.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dtos.request.UpdateUserRequest;
import africa.semicolon.promiscuous.dtos.response.*;
import africa.semicolon.promiscuous.exceptions.BadCredentialsException;
import africa.semicolon.promiscuous.exceptions.PromiscuousBaseException;
import africa.semicolon.promiscuous.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static africa.semicolon.promiscuous.utils.AppUtils.BLANK_SPACE;
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
    @Test
    public void testThatUserCanUpdateAccount(){
        UpdateUserRequest updateUserRequest = buildUpdateRequest();
        UpdateUserResponse updateUserResponse = userService.updateProfile(updateUserRequest, 500L);
        assertThat(updateUserResponse).isNotNull();
        GetUserResponse userResponse = userService.getUserById(500L);

        String fullName = userResponse.getFullName();
        String expectedFullName = new StringBuilder().append(updateUserRequest.getFirstName())
                .append(BLANK_SPACE)
                .append(updateUserRequest.getLastName())
                .toString();
        assertThat(fullName).isEqualTo(expectedFullName);
    }

    private UpdateUserRequest buildUpdateRequest() {
        Set<String> interests = Set.of("swimming", "sports","cooking");
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("Sherriff");
        updateUserRequest.setLastName("chris");
        updateUserRequest.setDateOfBirth(LocalDate.of(2005, Month.NOVEMBER.ordinal(),25));
//        MultipartFile testImage = getTestImage();
//        updateUserRequest.setProfileImage(testImage);
        updateUserRequest.setInterests(interests);
        return updateUserRequest;
    }

    private MultipartFile getTestImage(){
        //obtain a path that points to test image
        Path path = Paths.get("C:\\Users\\USER\\Desktop\\spring\\promiscuous\\src\\test\\resources\\images\\unsplash_jzz_3jWMzHA (1).png");
        //create stream that can read from file poinyed to by path
        try(InputStream inputStream = Files.newInputStream(path)) {
            //create a MultipartFile using bytes from file pointed to by path
            MultipartFile image = new MockMultipartFile("test_image",inputStream);
            return image;
        }catch (Exception exception){
            throw new PromiscuousBaseException("exception.getMessage()");
        }

    }
}
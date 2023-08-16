package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dtos.request.LoginRequest;
import africa.semicolon.promiscuous.dtos.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dtos.response.*;

import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    ApiResponse<?>activateUserAccount(String token);


    GetUserResponse getUserById(Long id);

    List<GetUserResponse> getAllUsers(int page, int pageSize);
    LoginResponse login(LoginRequest loginRequest);
}

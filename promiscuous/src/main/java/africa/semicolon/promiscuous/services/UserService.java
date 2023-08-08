package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dtos.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dtos.response.ActivateAccountResponse;
import africa.semicolon.promiscuous.dtos.response.ApiResponse;
import africa.semicolon.promiscuous.dtos.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    ApiResponse<?> activateUserAccount(String token);
}

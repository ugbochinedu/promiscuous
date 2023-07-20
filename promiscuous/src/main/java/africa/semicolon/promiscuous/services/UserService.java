package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dtos.RegisterUserRequest;
import africa.semicolon.promiscuous.dtos.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
}

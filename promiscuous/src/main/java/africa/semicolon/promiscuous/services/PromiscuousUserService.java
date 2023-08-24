package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.config.AppConfig;
import africa.semicolon.promiscuous.dtos.request.*;
import africa.semicolon.promiscuous.dtos.response.*;
import africa.semicolon.promiscuous.exceptions.AccountActivationFailedException;
import africa.semicolon.promiscuous.exceptions.BadCredentialsException;
import africa.semicolon.promiscuous.exceptions.PromiscuousBaseException;
import africa.semicolon.promiscuous.exceptions.UserNotFoundException;
import africa.semicolon.promiscuous.models.Address;
import africa.semicolon.promiscuous.models.Interest;
import africa.semicolon.promiscuous.models.User;
import africa.semicolon.promiscuous.repositories.UserRepository;
import africa.semicolon.promiscuous.services.cloud.CloudService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


import static africa.semicolon.promiscuous.dtos.response.ResponseMessage.ACCOUNT_ACTIVATION_SUCCESSFUL;
import static africa.semicolon.promiscuous.dtos.response.ResponseMessage.USER_REGISTRATION_SUCCESSFUL;
import static africa.semicolon.promiscuous.exceptions.ExceptionMessage.*;
import static africa.semicolon.promiscuous.utils.AppUtils.*;
import static africa.semicolon.promiscuous.utils.JwtUtil.*;

@Repository
@AllArgsConstructor
@Slf4j
public class PromiscuousUserService implements UserService{
    private final UserRepository userRepository;
    private final MailService mailService;
    private final AppConfig appConfig;
    private final CloudService cloudService;

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        String email = registerUserRequest.getEmail();
        String password = registerUserRequest.getPassword();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(new Address());
        User savedUser = userRepository.save(user);
        EmailNotificationRequest request = buildEmailRequest(savedUser);
        mailService.send(request);
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setId(savedUser.getId());
        registerUserResponse.setMessage(USER_REGISTRATION_SUCCESSFUL.name());
        return registerUserResponse;
    }




    @Override
    public ApiResponse<?>activateUserAccount(String token) {
        boolean isTestToken = token.equals(appConfig.getTestToken());
        if (isTestToken) return activateTestAccount();
        boolean isValidJwt = isValidJwt(token);
        if (isValidJwt) return activateAccount(token);
        throw new AccountActivationFailedException(
                ACCOUNT_ACTIVATION_FAILED_EXCEPTION.getMessage());
    }

    @Override
    public GetUserResponse getUserById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        User user = foundUser.orElseThrow(
                ()->new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage())
        );
        GetUserResponse getUserResponse = buildGetUserResponse(user);
        return getUserResponse;
    }

    @Override
    public List<GetUserResponse> getAllUsers(int page, int pageSize) {
        List<GetUserResponse> users = new ArrayList<>();
        Pageable pageable = buildPageRequest(page, pageSize);
        Page<User> usersPage = userRepository.findAll(pageable);
        List<User> foundUsers = usersPage.getContent();
//        for (User user:foundUsers) {
//            GetUserResponse getUserResponse = buildGetUserResponse(user);
//            users.add(getUserResponse);
//        }
//      return users;
        return foundUsers.stream()
                .map(user->buildGetUserResponse(user))
                .toList();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> foundUser = userRepository.readByEmail(email);
        User user = foundUser.orElseThrow(()-> new UserNotFoundException(
                String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(), email)));
        boolean isValidPassword = matches(user.getPassword(), password);
        if(isValidPassword){
            String accessToken = generateToken(email);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(accessToken);
            return loginResponse;
        }
        throw new BadCredentialsException(INVALID_CREDENTIALS_EXCEPTION.getMessage());
    }

//    @Override
//    public UpdateUserResponse updateUserProfile(JsonPatch jsonPatch, Long id){
//        ObjectMapper mapper = new ObjectMapper();
//        User user = findUserById(id);
//        JsonNode node = mapper.convertValue(user, JsonNode.class);
//        try{
//            JsonNode updateNode = jsonPatch.apply(node);
//            User updatedUser = mapper.convertValue(updateNode, User.class);
//            userRepository.save(updatedUser);
//            UpdateUserResponse response = new UpdateUserResponse();
//            response.setMessage("update successful");
//            return response;
//        }catch(JsonPatchException exception){
//            throw new PromiscuousBaseException(":()");
//        }
//    }

    @Override
    public UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest, Long id) {
        ModelMapper modelMapper = new ModelMapper();
        uploadImage(updateUserRequest.getProfileImage());

        ObjectMapper objectMapper = new ObjectMapper();
        //1convert user to jsonNode
        User user = findUserById(id);
        Set<String> userInterests = updateUserRequest.getInterests();
        Set<Interest> interests = parseInterestsFrom(userInterests);
        user.setInterests(interests);

        Address userAddress = user.getAddress();
        modelMapper.map(updateUserRequest, userAddress);
        user.setAddress(userAddress);
        JsonPatch updatePatch = buildUpdatePatch(updateUserRequest);
        JsonNode userNode = objectMapper.convertValue(user,JsonNode.class);
        try{
            //2Apply patch to JsinNode from step 1
            JsonNode updateNode = updatePatch.apply(userNode);
            //3Convert updatedNode to user
            user = objectMapper.convertValue(updateNode, User.class);
            //4save updatedUser from step 3 in the db
            userRepository.save(user);
            UpdateUserResponse updateUserResponse = new UpdateUserResponse();
            updateUserResponse.setMessage("Update Successful");
            return updateUserResponse;
        }catch (JsonPatchException exception){
            throw new PromiscuousBaseException(exception.getMessage());
        }
    }

    private void uploadImage(MultipartFile profileImage) {
        boolean isFormWithProfileImage = profileImage != null;
        if (isFormWithProfileImage) cloudService.upload(profileImage);
    }

    private static Set<Interest> parseInterestsFrom(Set<String> interests){
        Set<Interest> userInterests =  interests.stream()
                .map(interest -> Interest.valueOf(interest.toUpperCase()))
                .collect(Collectors.toSet());
        return userInterests;
    }

    private JsonPatch buildUpdatePatch(UpdateUserRequest updateUserRequest) {
        Field[] fields = updateUserRequest.getClass().getDeclaredFields();

        List<ReplaceOperation> operations=Arrays.stream(fields)
                .filter(field ->{
                    List<String> list = List.of("interests", "street", "houseNumber", "country","state","gender", "profileImage");
                    field.setAccessible(true);
                    try{
                        return field.get(updateUserRequest) != null && !list.contains(field.getName());
                    }catch (IllegalAccessException e){
                        throw new RuntimeException(e);
                    }
                })
                .map(field->{
                    field.setAccessible(true);
                    try {
                        String path = "/"+field.getName();
                        JsonPointer pointer = new JsonPointer(path);
                        String value = field.get(updateUserRequest).toString();
                        TextNode node = new TextNode(value);
                        ReplaceOperation operation = new ReplaceOperation(pointer, node);
                        return operation;
                    } catch (Exception exception) {
                        throw new RuntimeException(exception);
                    }
                }).toList();
        log.info("operations:: {}", operations);  // to sout
        List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
        return new JsonPatch(patchOperations);
    }


    private User findUserById(Long id){
        Optional<User> foundUser = userRepository.findById(id);
        User user = foundUser.orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));
        return user;
    }

    private Pageable buildPageRequest(int page, int pageSize) {
        if (page<1&&pageSize<1)return PageRequest.of(0, 10);
        if (page<1)return PageRequest.of(0, pageSize);
        if (pageSize<1) return PageRequest.of(page, pageSize);
        return PageRequest.of(page-1, pageSize);
    }


    private ApiResponse<?> activateAccount(String token) {
        String email = extractEmailFrom(token);
        Optional<User> user = userRepository.readByEmail(email);
        User foundUser = user.orElseThrow(()->new UserNotFoundException(
                String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(), email)
        ));
        foundUser.setActive(true);
        User savedUser = userRepository.save(foundUser);
        GetUserResponse userResponse = buildGetUserResponse(savedUser);
        var activateUserResponse = buildActivateUserResponse(userResponse);
        return ApiResponse.builder().data(activateUserResponse).build();
    }

    private static ActivateAccountResponse buildActivateUserResponse(GetUserResponse userResponse) {
        return ActivateAccountResponse.builder()
                .message(ACCOUNT_ACTIVATION_SUCCESSFUL.name())
                .user(userResponse)
                .build();
    }

    private static GetUserResponse buildGetUserResponse(User savedUser) {
        return GetUserResponse.builder()
                .id(savedUser.getId())
                .address(savedUser.getAddress().toString())
                .fullName(getFullName(savedUser))
                .phoneNumber(savedUser.getPhoneNumber())
                .email(savedUser.getEmail())
                .build();
    }

    private static String getFullName(User savedUser) {
        return savedUser.getFirstName() + BLANK_SPACE + savedUser.getLastName();
    }

    private static ApiResponse<?> activateTestAccount() {
        return ApiResponse.builder()
                .build();
    }


    private EmailNotificationRequest buildEmailRequest(User savedUser){
        EmailNotificationRequest request =new EmailNotificationRequest();
        List<Recipient> recipients = new ArrayList<>();
        Recipient recipient = new Recipient(savedUser.getEmail());
        recipients.add(recipient);
        request.setRecipients(recipients);
        request.setSubject(WELCOME_MAIL_SUBJECT);
        String activationLink =
                generateActivationLink(appConfig.getBaseUrl(), savedUser.getEmail());
        String emailTemplate = getMailTemplate();
        String mailContent = String.format(emailTemplate, activationLink);
        request.setMailContent(mailContent);
        return request;
    }
}
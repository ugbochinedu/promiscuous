package africa.semicolon.promiscuous.controllers;

import africa.semicolon.promiscuous.dtos.request.FindUserRequest;
import africa.semicolon.promiscuous.dtos.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dtos.request.UpdateUserRequest;
import africa.semicolon.promiscuous.dtos.response.GetUserResponse;
import africa.semicolon.promiscuous.dtos.response.RegisterUserResponse;
import africa.semicolon.promiscuous.dtos.response.UpdateUserResponse;
import africa.semicolon.promiscuous.services.UserService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest registerUserRequest){
        RegisterUserResponse response = userService.register(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/findById")
    public ResponseEntity<GetUserResponse> getUserById(@RequestBody FindUserRequest request){
        long id = request.getId();
        GetUserResponse response = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<GetUserResponse>> getAllUser(@RequestBody FindUserRequest request){
        int page = request.getPage();
        int pageSize = request.getPageSize();
        List<GetUserResponse> response = userService.getAllUsers(page,pageSize);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<UpdateUserResponse> updateUserAccount(@RequestBody JsonPatch jsonPatch, @PathVariable Long id){
//        UpdateUserResponse response = userService.updateUserProfile(jsonPatch, id);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUserProfile(@ModelAttribute UpdateUserRequest updateUserRequest,
                                                                @PathVariable Long id){
        UpdateUserResponse response = userService.updateProfile(updateUserRequest, id);
        return ResponseEntity.ok(response);
    }
}
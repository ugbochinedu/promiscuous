package africa.semicolon.promiscuous.controllers;

import africa.semicolon.promiscuous.dtos.request.*;
import africa.semicolon.promiscuous.dtos.response.GetUserResponse;
import africa.semicolon.promiscuous.dtos.response.RegisterUserResponse;
import africa.semicolon.promiscuous.dtos.response.UpdateUserResponse;
import africa.semicolon.promiscuous.dtos.response.UploadMediaResponse;
import africa.semicolon.promiscuous.models.Reaction;
import africa.semicolon.promiscuous.services.UserService;
import africa.semicolon.promiscuous.services.cloud.MediaService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final MediaService mediaService;

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

    @PostMapping("/uploadMedia")
    public ResponseEntity<UploadMediaResponse> uploadMedia(@ModelAttribute UploadMediaRequest mediaRequest){
        MultipartFile mediaToUpload = mediaRequest.getMedia();
        UploadMediaResponse response = mediaService.uploadMedia(mediaToUpload);
        return ResponseEntity.ok(response);
    }
    @PostMapping("uploadProfilePicture")
    public ResponseEntity<UploadMediaResponse> uploadProfilePicture(@ModelAttribute UploadMediaRequest mediaRequest){
        MultipartFile mediaToUpload = mediaRequest.getMedia();
        UploadMediaResponse response = mediaService.uploadProfilePicture(mediaToUpload);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/likeOrDislike/{id}")
    public ResponseEntity<?> likeOrDislike(@RequestBody LikeOrDislikeRequest userReaction, @PathVariable Long id){
        Reaction mediaReaction = userReaction.getReaction();
        String response = mediaService.likeOrDislike(mediaReaction,id);
        return ResponseEntity.ok(response);
    }
}
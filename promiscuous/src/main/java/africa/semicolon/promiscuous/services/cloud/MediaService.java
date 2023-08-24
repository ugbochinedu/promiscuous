package africa.semicolon.promiscuous.services.cloud;

import africa.semicolon.promiscuous.dtos.response.UploadMediaResponse;
import africa.semicolon.promiscuous.models.Reaction;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    UploadMediaResponse uploadProfilePicture(MultipartFile file);
    UploadMediaResponse uploadMedia(MultipartFile file);
    String likeOrDislike(Reaction reaction, Long userId);
}
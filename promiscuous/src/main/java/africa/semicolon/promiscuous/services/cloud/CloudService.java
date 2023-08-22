package africa.semicolon.promiscuous.services.cloud;

import africa.semicolon.promiscuous.dtos.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String upload(MultipartFile file);
}

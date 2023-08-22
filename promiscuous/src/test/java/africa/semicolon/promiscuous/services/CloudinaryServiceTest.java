package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dtos.response.ApiResponse;
import africa.semicolon.promiscuous.services.cloud.CloudinaryService;
import africa.semicolon.promiscuous.utils.AppUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CloudinaryServiceTest {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Test
    public void testUpLoadFile(){
        Path path = Paths.get(AppUtils.TEST_IMAGE_LOCATION);
        try(InputStream inputStream = Files.newInputStream(path)){
            MultipartFile file = new MockMultipartFile("testImage", inputStream);
            String response = cloudinaryService.upload(file);
            assertNotNull(response);
            assertThat(response).isNotNull();
        }catch (IOException exception){
            throw new RuntimeException(":(");
        }
    }
}

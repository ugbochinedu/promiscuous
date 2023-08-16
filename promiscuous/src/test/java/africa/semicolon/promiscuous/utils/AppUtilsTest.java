package africa.semicolon.promiscuous.utils;

import africa.semicolon.promiscuous.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static africa.semicolon.promiscuous.utils.AppUtils.generateActivationLink;
import static africa.semicolon.promiscuous.utils.JwtUtil.generateToken;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class AppUtilsTest {

    @Autowired
    private AppConfig appConfig;
    @Test
    public void testGenerateActivationLink(){
        String activationLink = generateActivationLink(appConfig.getBaseUrl(), "test@email.com");
        log.info("activationLink ->{}", activationLink);
        assertThat(activationLink).isNotNull();
        assertThat(activationLink).contains("http://localhost:8080/activate");
    }

    @Test
    public void generateTokenTest(){
        String email = "test@gmail.com";
        String token = generateToken(email);
        log.info("generated token->{}", token);
        assertThat(token).isNotNull();
    }
}

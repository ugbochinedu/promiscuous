package africa.semicolon.promiscuous.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${mail.api.key}")
    private String mailApiKey;

    @Value("${app.dev.token}")
    private String testToken;
    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${cloud.api.name}")
    private String cloudApiName;

    @Value("${cloud.api.key}")
    private String cloudApiKey;

    @Value("${cloud.api.secret}")
    private String cloudApiSecret;

    public String getCloudApiName() {
        return cloudApiName;
    }

    public String getCloudApiKey() {
        return cloudApiKey;
    }

    public String getCloudApiSecret() {
        return cloudApiSecret;
    }

    public String getMailApiKey() {
        return mailApiKey;
    }

    public String getTestToken() {
        return this.testToken;
    }
    public String getBaseUrl(){
        return baseUrl;
    }
}

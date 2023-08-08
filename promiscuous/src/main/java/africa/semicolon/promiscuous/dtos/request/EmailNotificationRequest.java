package africa.semicolon.promiscuous.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificationRequest {
    private Sender sender;
    @JsonProperty("to")
    private List<Recipient> recipients;
    @JsonProperty("cc")
    private List<String> copiedEmails;
    @JsonProperty("htmlContent")
    private String mailContent;

    private String textContent;
    private String subject;
}

package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dtos.request.EmailNotificationRequest;
import africa.semicolon.promiscuous.dtos.response.EmailNotificationResponse;

public interface MailService {
    EmailNotificationResponse send(EmailNotificationRequest emailNotificationRequest);
}

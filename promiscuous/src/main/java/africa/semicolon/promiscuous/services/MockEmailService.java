package africa.semicolon.promiscuous.services;

public class MockEmailService {

    public static String sendEmail(String email){
        if(email!= null) return "Email sent successfully";
        throw new RuntimeException("Email sending failed");
    }
}

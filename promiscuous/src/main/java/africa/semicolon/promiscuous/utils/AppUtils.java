package africa.semicolon.promiscuous.utils;

import africa.semicolon.promiscuous.exceptions.PromiscuousBaseException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import static africa.semicolon.promiscuous.utils.JwtUtil.generateToken;

public class  AppUtils {

    public static final String APP_NAME = "promiscuous inc.";
    public static final String APP_EMAIL = "noreply@promiscuous.africa";
    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\USER\\Desktop\\spring\\promiscuous\\src\\main\\resources\\images\\L2u9aY5uuAOp1STLEzjhy3ttStLVC00wR7cpxesT.jpg";

    private static final String MAIL_TEMPLATE_LOCATION = "C:\\Users\\USER\\Desktop\\spring\\promiscuous\\src\\main\\resources\\templates\\index.html";

    public static final String WELCOME_MAIL_SUBJECT = "Welcome to promiscuous inc.";

    public static final String BLANK_SPACE=" ";

    public static final String EMPTY_STRING="";

    private static final String ACTIVATE_ACCOUNT_PATH="/user/activate?code=";

    public static String generateActivationLink(String baseUrl, String email){
        String token = generateToken(email);
        //localhost:8080/user/activate?code=xxxxxxxxxxxx
        String activationLink = baseUrl+ACTIVATE_ACCOUNT_PATH+token;
        return activationLink;
    }




    public static String getMailTemplate() {
        Path templateLocation = Paths.get(MAIL_TEMPLATE_LOCATION);
        try {
            List<String> fileContents = Files.readAllLines(templateLocation);
            String template = String.join(EMPTY_STRING, fileContents);
            return template;
        }catch (IOException exception){
            throw new PromiscuousBaseException(exception.getMessage());
        }
    }

    public static boolean matches(String first, String second){
        return first.equals(second);
    }
}

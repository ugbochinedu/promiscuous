package africa.semicolon.promiscuous.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;

import static africa.semicolon.promiscuous.utils.AppUtils.APP_NAME;


public class JwtUtil {

    public static String generateToken(String email){
        String token = JWT.create()
                .withClaim("user", email)
                .withIssuer(APP_NAME)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(Algorithm.HMAC512("secret"));
        return token;
    }

    public static boolean isValidJwt(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret"))
                .withIssuer(APP_NAME)
                .withClaimPresence("user")
                .build();

        return verifier.verify(token)!=null;
    }

    public static String extractEmailFrom(String token){
        var claim = JWT.decode(token).getClaim("user");
        return (String) claim.asMap().get("user");
    }
}
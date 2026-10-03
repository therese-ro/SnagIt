package idatt2105.project.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Duration;
import java.time.Instant;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenHandling {
  private static final String KEY = "secretKeyToUseForTokensAndIsTopSecretSoDontTellAnyone";
  private static final Duration TOKEN_VALIDITY_DUR = Duration.ofMinutes(10);
  private static final Algorithm hmac512 = Algorithm.HMAC512(KEY);
  private static final JWTVerifier verifier = JWT.require(hmac512).build();

  public static String generateToken(String userID) {
    final Instant now = Instant.now();
    return JWT.create()
        .withSubject(userID)
        .withIssuer("idatt2105_project_backend")
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(TOKEN_VALIDITY_DUR.toMillis()))
        .sign(hmac512);
  };

  public static String validateTokenAndGetUserId(String token) {
    try {
      return verifier.verify(token).getSubject();
    } catch (final JWTVerificationException verificationEx) {
      return null;
    }
  }

  public static int getIdFromSecurityContext() throws RuntimeException {
    try {
      //Got help from ChatGPT on how to retrieve the ID, as well as by surfing the web
      String userIDFromToken =
          SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
      return Integer.parseInt(userIDFromToken);
    } catch (Exception e) {
      throw new RuntimeException("A problem occurred retrieving user information.");
    }
  }
}

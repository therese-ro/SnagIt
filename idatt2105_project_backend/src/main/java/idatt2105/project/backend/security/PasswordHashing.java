package idatt2105.project.backend.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {
  //TODO: Improve the encryption. For example add salt.
  //TODO: In sql-inserts it is possible to use VALUES ('mail@mail.no', SHA2('thePassword', 256) since this is the one we use here
  //Code below from: https://medium.com/@mbanaee61/password-encryption-hashing-and-salting-in-java-eaa2d46b06e5
  public static String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] messageDigest = md.digest(password.getBytes());

      StringBuilder sb = new StringBuilder();

      for (byte b : messageDigest) {
        sb.append(String.format("%02x", b));
      }

      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      //forbedre exception-melding???
      throw new RuntimeException("There was an issue with handling the password");
    }

  }
}

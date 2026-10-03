package idatt2105.project.backend.service;

import idatt2105.project.backend.model.User;
import idatt2105.project.backend.model.UserCredentials;
import idatt2105.project.backend.model.UserRegistration;
import idatt2105.project.backend.repository.UserCredentialsRepository;
import idatt2105.project.backend.repository.UserRepository;
import idatt2105.project.backend.security.PasswordHashing;
import idatt2105.project.backend.util.TokenHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserCredentialsRepository userCredentialsRepository;

  public String login(UserCredentials userCredentials) {
    try {
      validateUserCredentials(userCredentials);
    } catch (IllegalArgumentException e) {
      return "";
    }

    String email = userCredentials.getEmail();
    String password = userCredentials.getPassword();

    try {
      User user = userRepository.getUserByEmail(email);
      if (user == null) {
        return "";
      } else {
        String hashedPasswordDb = userCredentialsRepository.getPassword(user.getUserId());
        String hashedCredentialPassword = PasswordHashing.hashPassword(password);
        if(!hashedPasswordDb.equals(hashedCredentialPassword)) {
          return "";
        }
      return TokenHandling.generateToken(String.valueOf(user.getUserId()));
    }
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to log in.");
    }
  }

  public String registerUser(UserRegistration userRegistration) throws RuntimeException {
    try {
      validateUserRegistration(userRegistration);
    } catch (IllegalArgumentException e) {
      return "";
    }

    String displayName = userRegistration.getDisplayName();
    String email = userRegistration.getEmail();
    String password = userRegistration.getPassword();

    //Exception handling and such below should be improved.
    try {
      if (userRepository.getUserByEmail(email) == null) {
        userRepository.insertUser(displayName, email);

        User user = userRepository.getUserByEmail(email);
        int userID = user.getUserId();
        String hashedPassword = PasswordHashing.hashPassword(password);

        userCredentialsRepository.insertUserCredentials(userID, hashedPassword);
        return TokenHandling.generateToken(String.valueOf(userID));
      }
      return "";
    } catch (Exception e) {
      throw new RuntimeException("An error occurred while registering a new user and returning token.");
    }

  }

  private void validateUserRegistration(UserRegistration userRegistration) {
    if (userRegistration.getDisplayName() == null || userRegistration.getDisplayName().isEmpty()) {
      throw new IllegalArgumentException();
    }
    if (userRegistration.getEmail() == null || userRegistration.getEmail().isEmpty()) {
      throw new IllegalArgumentException();
    }
    if (userRegistration.getPassword() == null || userRegistration.getPassword().isEmpty()) {
      throw new IllegalArgumentException();
    }
  }

  private void validateUserCredentials(UserCredentials userCredentials) throws IllegalArgumentException {
    if (userCredentials.getEmail() == null || userCredentials.getEmail().isEmpty()) {
      throw new IllegalArgumentException();
    }
    if (userCredentials.getPassword() == null || userCredentials.getPassword().isEmpty()) {
      throw new IllegalArgumentException();
    }
  }
}

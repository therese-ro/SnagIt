package idatt2105.project.backend.service;

import idatt2105.project.backend.model.User;
import idatt2105.project.backend.repository.UserCredentialsRepository;
import idatt2105.project.backend.repository.UserRepository;
import idatt2105.project.backend.security.PasswordHashing;
import idatt2105.project.backend.util.TokenHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserCredentialsRepository userCredentialsRepository;

  public User getPersonalInfo() throws RuntimeException {
    try {
      int userIDFromSecurityContext = TokenHandling.getIdFromSecurityContext();
      User user = userRepository.getUserByID(userIDFromSecurityContext);

      //sets the id to 0 to improve security and prevent others from getting this information
      user.setUserId(0);

      return user;
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to retrieve the personal information.");
    }
  }

  public void updateEmail(String email) throws RuntimeException {
    try {
      //This could have been separate, but to reduce the information attackers may access, no
      //information about whether an email is 'taken' will be given.
      User user = userRepository.getUserByEmail(email);
      if (user != null) {
        throw new IllegalArgumentException();
      }

      int userIDFromSecurityContext = TokenHandling.getIdFromSecurityContext();
      userRepository.updateUserEmail(userIDFromSecurityContext, email);
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to update email.");
    }
  }

  public void updateDisplayName(String displayName) throws RuntimeException {
    try {
      int userIDFromSecurityContext = TokenHandling.getIdFromSecurityContext();
      userRepository.updateDisplayName(userIDFromSecurityContext, displayName);
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to update display name.");
    }
  }

  public void updatePassword(String oldPassword, String newPassword, String newPasswordAgain)
      throws RuntimeException {
    try {
      int userIDFromSecurityContext = TokenHandling.getIdFromSecurityContext();
      String oldPasswordDb = userCredentialsRepository.getPassword(userIDFromSecurityContext);

      //All checks will throw if invalid here, since having specific messages about what went wrong
      //risks attackers gaining critical information.
      if (PasswordHashing.hashPassword(oldPassword).equals(oldPasswordDb) &&
        newPassword.equals(newPasswordAgain)) {
        userCredentialsRepository.updatePassword(userIDFromSecurityContext,
            PasswordHashing.hashPassword(newPassword));
      } else {
        throw new IllegalArgumentException();
      }
    } catch (Exception e) {
      throw new RuntimeException("An error occurred while updating password.");
    }
  }

}

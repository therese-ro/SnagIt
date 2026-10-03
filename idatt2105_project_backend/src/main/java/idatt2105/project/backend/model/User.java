package idatt2105.project.backend.model;

/**
 * Model-class for user.
 *
 * @author SnagIt Duo
 */
public class User {
  private int userId;
  private String displayName;
  private String email;

  public User(int userId, String displayName, String email) {
    this.userId = userId;
    this.displayName = displayName;
    this.email = email;
  }

  public int getUserId() {
    return userId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getEmail() {
    return email;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

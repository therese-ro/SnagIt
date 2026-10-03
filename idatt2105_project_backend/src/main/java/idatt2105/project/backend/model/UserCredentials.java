package idatt2105.project.backend.model;

/**
 * Class for credentials related to the user for login-purposes.
 *
 * @author SnagIt Duo
 */
public class UserCredentials{
  private String email;
  private String password;

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

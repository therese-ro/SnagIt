package idatt2105.project.backend.repository;

import idatt2105.project.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public User getUserByEmail(String email) {
    String querySelectByEmail = "SELECT * FROM Users WHERE email = ?";
    try {
      return jdbcTemplate.queryForObject(querySelectByEmail,
          (rs, rowNum) -> new User(rs.getInt("userID"),
              rs.getString("displayName"), rs.getString("email")), email);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public User getUserByID(int userID) {
    String querySelectByID = "SELECT * FROM Users WHERE userID = ?";

    return jdbcTemplate.queryForObject(querySelectByID, (rs, rowNum) -> new User(rs.getInt("userID"),
        rs.getString("displayName"), rs.getString("email")), userID);
  }

  public void updateUserEmail(int userID, String newEmail) {
    String queryUpdateEmail = "UPDATE Users SET email = ? WHERE userID = ?";
    jdbcTemplate.update(queryUpdateEmail, newEmail, userID);
  }

  public void updateDisplayName(int userID, String newDisplayName) {
    String queryUpdateDisplayName = "UPDATE Users SET displayName = ? WHERE userID = ?";
    jdbcTemplate.update(queryUpdateDisplayName, newDisplayName, userID);
  }

  public void insertUser(String displayName, String email) {
    String queryInsertUser = "INSERT INTO Users (displayName, email) VALUES (?, ?)";
    jdbcTemplate.update(queryInsertUser, displayName, email);
  }

}

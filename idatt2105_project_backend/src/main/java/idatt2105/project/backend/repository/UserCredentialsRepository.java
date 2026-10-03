package idatt2105.project.backend.repository;

import idatt2105.project.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserCredentialsRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public String getPassword(int userID) {
    String querySelectPassword = "SELECT password FROM UserCredentials WHERE userID = ?";

    return jdbcTemplate.queryForObject(querySelectPassword, (rs, rowNum) ->
      rs.getString("password"), userID);
  }

  //update osv. returnerer hvor mange rader som har blitt endret, hadde det vært relevant
  //å ha logger her som logger hvor mange rader som blir oppdatert? vil jo gjøre
  //det lettere å feilsøke og bekrefte hva som skjer/ikke skjer
  public void updatePassword(int userID, String password) {
    String queryUpdatePassword = "UPDATE UserCredentials SET password = ? WHERE userID = ?";

    jdbcTemplate.update(queryUpdatePassword, password, userID);
  }

  public void insertUserCredentials(int userID, String password) {
    String queryInsertUser = "INSERT INTO UserCredentials (userID, password) VALUES (?, ?)";
    jdbcTemplate.update(queryInsertUser, userID, password);
  }
}

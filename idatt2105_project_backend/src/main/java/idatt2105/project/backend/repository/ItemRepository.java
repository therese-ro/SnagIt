package idatt2105.project.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//håndterer sletting av items, oppdatering og laging
//skal nok ikke være særlig med henting her
@Repository
public class ItemRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  //Also consider whether there should be a certain control on whether a categID exists
  public void insertItem(String shortDesc, String longDesc, int price, double longitude,
                         double latitude, String thumbnailPath, int categID,
                         int userID) {
    String queryInsertItem = "INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, " +
        "thumbnailPath, categID, userID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    //date, forsale and itemID are made automatically
    jdbcTemplate.update(queryInsertItem, shortDesc, longDesc, price, longitude, latitude,
          thumbnailPath, categID, userID);
  }

  public void updateItem(int itemID, String shortDesc, String longDesc, int price, double longitude,
                         double latitude, boolean isForSale,
                         String thumbnailPath, int categID) {
    String queryUpdateItem = "UPDATE Item SET shortDesc = ?, longDesc = ?, price = ?, " +
        "longitude = ?, latitude = ?, isForSale = ?, thumbnailPath = ?, " +
        "categID = ? WHERE itemID = ?";
    jdbcTemplate.update(queryUpdateItem, shortDesc, longDesc, price, longitude, latitude,
        isForSale, thumbnailPath,categID, itemID);
  }

  public void deleteItem(int itemID) {
    String queryDeleteItem = "DELETE FROM Item WHERE itemID = ?";
    jdbcTemplate.update(queryDeleteItem, itemID);
  }

  public int getUserID(int itemID) {
    String querySelectUserID = "SELECT userID FROM Item WHERE itemID = ?";
    try {
      return jdbcTemplate.queryForObject(querySelectUserID, (rs, rowNum) -> rs.getInt("userID"),
          itemID);
    } catch (Exception e) {
      return -1;
    }
  }
}

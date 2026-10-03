package idatt2105.project.backend.repository;

import idatt2105.project.backend.model.Category;
import idatt2105.project.backend.model.Listing;
import idatt2105.project.backend.model.ShortenedListing;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//Denne klassen har kun select-setninger, og joiner item, category og user
//This only class has select-statements. Insert, update and delete are handled in itemRepo.
@Repository
public class ListingRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public Listing getListingByItemID(int itemID) {
    //kjører vanlig join fordi det er et 1-1 forhold mellom item og category og item og user. Altså
    //MÅ en item ha både userID og categID, som gjør at JOIN skal gå fint
    String querySelectListingByItemID =
        "SELECT I.itemID, I.shortDesc, I.longDesc, I.price, I.longitude, I.latitude, " +
            "I.createdAt, I.isForSale, I.thumbnailPath, C.categoryName, " +
            "C.categID AS categID, U.displayName, U.email, U.userID AS userID FROM Item I " +
        "JOIN Category C ON I.categID = C.categID " +
        "JOIN Users U ON I.userID = U.userID " +
        "WHERE itemID = ?";

    return jdbcTemplate.queryForObject(querySelectListingByItemID,
        (rs, rowNum) -> {
            Listing listing = new Listing();

            Category category = new Category();
            category.setCategoryID(rs.getInt("categID"));
            category.setCategoryName(rs.getString("categoryName"));

            listing.setItemID(rs.getInt("itemID"));
            listing.setShortDesc(rs.getString("shortDesc"));
            listing.setLongDesc(rs.getString("longDesc"));
            listing.setPrice(rs.getInt("price"));
            listing.setLongitude(rs.getDouble("longitude"));
            listing.setLatitude(rs.getDouble("latitude"));
            listing.setCreatedAt(rs.getDate("createdAt"));
            listing.setIsForSale(rs.getBoolean("isForSale"));
            listing.setThumbnailPath(rs.getString("thumbnailPath"));
            listing.setCategory(category);
            listing.setSellerDisplayName(rs.getString("displayName"));
            listing.setSellerEmail(rs.getString("email"));

            return listing;
          }, itemID);
  }

  //Got help from ChatGPT to use MySQL-syntax with LIMIT and OFFSET. Used this in the following
  //methods as well.
  public List<ShortenedListing> getAllShortenedListingsForUserPaginated(int userID, int pagesize, int page) {
    int offset = (page - 1) * pagesize;
    String querySelectAllListingsFromUser = "SELECT itemID, shortDesc, price, longitude, " +
        "latitude, thumbnailPath FROM Item WHERE userID = ? " +
        "ORDER BY createdAt DESC LIMIT ? OFFSET ?";

    return jdbcTemplate.query(querySelectAllListingsFromUser, (rs, rowNum) -> {
      ShortenedListing shortenedListing = new ShortenedListing();
      shortenedListing.setItemID(rs.getInt("itemID"));
      shortenedListing.setShortDesc(rs.getString("shortDesc"));
      shortenedListing.setPrice(rs.getInt("price"));
      shortenedListing.setLongitude(rs.getDouble("longitude"));
      shortenedListing.setLatitude(rs.getDouble("latitude"));
      shortenedListing.setThumbnailPath(rs.getString("thumbnailPath"));

      return shortenedListing;
      }, userID, pagesize, offset);
  }

  public List<ShortenedListing> getShortenedListingsPaginated(int pagesize, int page) {
    int offset = (page - 1) * pagesize;

    String querySelectAllListings = "SELECT itemID, shortDesc, price, longitude, " +
        "latitude, thumbnailPath FROM Item WHERE isForSale = true " +
        "ORDER BY createdAt DESC LIMIT ? OFFSET ?";

    return jdbcTemplate.query(querySelectAllListings,
        (rs, rowNum) -> {
            ShortenedListing shortenedListing = new ShortenedListing();

            shortenedListing.setItemID(rs.getInt("itemID"));
            shortenedListing.setShortDesc(rs.getString("shortDesc"));
            shortenedListing.setPrice(rs.getInt("price"));
            shortenedListing.setLongitude(rs.getDouble("longitude"));
            shortenedListing.setLatitude(rs.getDouble("latitude"));
            shortenedListing.setThumbnailPath(rs.getString("thumbnailPath"));

            return shortenedListing;
          }, pagesize, offset);
  };

  public List<ShortenedListing> getShortenedListingsFromCategoryPaginated(int categID, int pagesize, int page) {
    int offset = (page - 1) * pagesize;
    String querySelectAllListingsFromCategory = "SELECT itemID, shortDesc, price, longitude, " +
        "latitude, thumbnailPath FROM Item WHERE categID = ? AND isForSale = true " +
        "ORDER BY createdAt DESC LIMIT ? OFFSET ?";

      return jdbcTemplate.query(querySelectAllListingsFromCategory,
          (rs, rowNum) -> {
            ShortenedListing shortenedListing = new ShortenedListing();

            shortenedListing.setItemID(rs.getInt("itemID"));
            shortenedListing.setShortDesc(rs.getString("shortDesc"));
            shortenedListing.setPrice(rs.getInt("price"));
            shortenedListing.setLongitude(rs.getDouble("longitude"));
            shortenedListing.setLatitude(rs.getDouble("latitude"));
            shortenedListing.setThumbnailPath(rs.getString("thumbnailPath"));

            return shortenedListing;
          }, categID, pagesize, offset);
  };

  public int countListings() {
    String queryCountListings = "SELECT COUNT(itemID) as countItems FROM Item WHERE isForSale = true ";
      return jdbcTemplate.queryForObject(queryCountListings,
          (rs, rowNum) -> rs.getInt("countItems"));
  }

  public int countListingsFromUser(int userID) {
    String queryCountListingFromUser = "SELECT COUNT(itemID) as countItems FROM Item WHERE userID = ? ";
      return jdbcTemplate.queryForObject(queryCountListingFromUser,
          (rs, rowNum) -> rs.getInt("countItems"), userID);
  }

  public int countListingsForCategory(int categID) {
    String querySelectLastListingByCategory = "SELECT COUNT(itemID) AS countItems FROM Item WHERE categID = ? " +
        "AND isForSale = true";
    return jdbcTemplate.queryForObject(querySelectLastListingByCategory,
        (rs, rowNum) -> rs.getInt("countItems"), categID);
  };
}

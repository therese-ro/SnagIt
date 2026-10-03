package idatt2105.project.backend.model;

/**
 * Model-class for a shortened version of a listing.
 *
 * @author SnagIt Duo
 */
public class ShortenedListing {
  private int itemID;
  private String shortDesc;
  private int price;
  private double longitude;
  private double latitude;
  private String thumbnailPath;

  public int getItemID() {
    return itemID;
  }

  public String getShortDesc() {
    return shortDesc;
  }

  public int getPrice() {
    return price;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public String getThumbnailPath() {
    return thumbnailPath;
  }

  public void setItemID(int itemID) {
    this.itemID = itemID;
  }

  public void setShortDesc(String shortDesc) {
    this.shortDesc = shortDesc;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void setThumbnailPath(String thumbnailPath) {
    this.thumbnailPath = thumbnailPath;
  }
}

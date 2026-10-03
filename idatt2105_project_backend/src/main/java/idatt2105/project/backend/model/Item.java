package idatt2105.project.backend.model;

import java.util.Date;

/**
 * Model-class for an item.
 *
 * @author SnagIt Duo
 */
public class Item {
  private int itemID;
  private String shortDesc;
  private String longDesc;
  private int price;
  private double longitude;
  private double latitude;
  private Date createdAt;
  private String thumbnailPath;
  private Boolean isForSale;

  private int categoryID;
  private int userID;

  public int getItemID() {
    return itemID;
  }

  public String getShortDesc() {
    return shortDesc;
  }

  public String getLongDesc() {
    return longDesc;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  public Boolean getIsForSale() {
    return isForSale;
  }

  public String getThumbnailPath() {
    return thumbnailPath;
  }

  public int getCategoryID() {
    return categoryID;
  }

  public int getUserID() {
    return userID;
  }

  public void setItemID(int itemID) {
    this.itemID = itemID;
  }

  public void setShortDesc(String shortDesc) {
    this.shortDesc = shortDesc;
  }

  public void setLongDesc(String longDesc) {
    this.longDesc = longDesc;
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

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setForSale(Boolean isForSale) {
    this.isForSale = isForSale;
  }

  public void setThumbnailPath(String thumbnailPath) {
    this.thumbnailPath = thumbnailPath;
  }

  public void setCategoryID(int categoryID) {
    this.categoryID = categoryID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }
}

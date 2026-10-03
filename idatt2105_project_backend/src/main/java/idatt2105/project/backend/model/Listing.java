package idatt2105.project.backend.model;

import java.util.Date;

/**
 * Model-class for extensive information about a listing. Includes important information
 * related to the item in the listing, as well as the seller.
 *
 * @author SnagIt Duo
 */
public class Listing {
  private int itemID;
  private String shortDesc;
  private String longDesc;
  private int price;
  private double longitude;
  private double latitude;
  private Date createdAt;
  private Boolean isForSale;
  private String thumbnailPath;

  private Category category;

  private String sellerDisplayName;
  private String sellerEmail;
  private Boolean isSeller;

  public Listing()
  {
    this.isSeller = false;
  }

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

  public Category getCategory() {
    return category;
  }

  public String getSellerDisplayName() {
    return sellerDisplayName;
  }

  public String getSellerEmail() {
    return sellerEmail;
  }

  public Boolean getIsSeller() {
    return isSeller;
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

  public void setIsForSale(Boolean isForSale) {
    this.isForSale = isForSale;
  }

  public void setThumbnailPath(String thumbnailPath) {
    this.thumbnailPath = thumbnailPath;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setSellerDisplayName(String sellerDisplayName) {
    this.sellerDisplayName = sellerDisplayName;
  }

  public void setSellerEmail(String sellerEmail) {
    this.sellerEmail = sellerEmail;
  }

  public void setIsSeller(Boolean isSeller) {
    this.isSeller = isSeller;
  }
}

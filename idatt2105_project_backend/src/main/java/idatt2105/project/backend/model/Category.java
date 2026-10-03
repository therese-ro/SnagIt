package idatt2105.project.backend.model;

/**
 * Model-class for a category. Includes id and name of the category, as well
 * as getters and setters.
 *
 * @author SnagIt Duo
 */
public class Category {
  private int categoryID;
  private String categoryName;

  public int getCategoryID() {
    return categoryID;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryID(int categoryID) {
    this.categoryID = categoryID;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
}

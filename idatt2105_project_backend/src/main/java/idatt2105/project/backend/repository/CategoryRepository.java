package idatt2105.project.backend.repository;

import idatt2105.project.backend.model.Category;
import idatt2105.project.backend.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Category> getAllCategories() {
    String querySelectByCategID = "SELECT * FROM Category";
      return jdbcTemplate.query(querySelectByCategID,
          (rs, rowNum) -> {
            Category category = new Category();
            category.setCategoryID(rs.getInt("categID"));
            category.setCategoryName(rs.getString("categoryName"));
            return category;
          });
  }
}

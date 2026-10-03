package idatt2105.project.backend.service;

import idatt2105.project.backend.model.Category;
import idatt2105.project.backend.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  public List<Category> getAllCategories() throws RuntimeException {
    try {
      return categoryRepository.getAllCategories();
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to retrieve the categories.");
    }
  }


}

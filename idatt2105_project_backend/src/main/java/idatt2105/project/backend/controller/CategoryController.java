package idatt2105.project.backend.controller;

import idatt2105.project.backend.model.Category;
import idatt2105.project.backend.model.User;
import idatt2105.project.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for requests related to only categories.
 * Simple for now, but ability to add, update or delete categories will hopefully be
 * implemented in the future.
 *
 * @author SnagIt-duo
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/category")
public class CategoryController {

  @Autowired
  CategoryService categoryService;

  @Operation(summary = "Get all categories.",
      description = "Get all categories in a list")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true, content = {@Content(mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = Category.class)))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @GetMapping("/allCategories")
  public ResponseEntity<Object> getAllCategories() {
    try {
      return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error", e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}

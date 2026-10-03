package idatt2105.project.backend.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2105.project.backend.model.Category;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

//The tests use the data in the data.sql file found in src/main/resources/sql and hard-code it in.
//It is not ideal, but the best we could to for tests with the time left.
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getAllCategoriesReturnsStatus200AndCorrectResult() throws Exception {
    MvcResult result = mockMvc.perform(get("/category/allCategories"))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String json = result.getResponse().getContentAsString();
    List<Category> categories = objectMapper.readValue(json, new TypeReference<List<Category>>() {
    });

    assertNotNull(categories);
    //There are 6 categories inserted in data.sql, so this is what is referenced.
    assertEquals(6, categories.size());
  }
}

package idatt2105.project.backend.integrationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

//The tests use the data in the data.sql file found in src/main/resources/sql and hard-code it in.
//It is not ideal, but the best we could to for tests with the time left.
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  //Using transactional ensures that the sql-transaction is rolled back when the method has
  //completed its tasks.
  @Transactional
  @Test
  void registerNewUserWithValidInputReturnsStatus200() throws Exception {
    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
            .content("{\"displayName\": \"Navn Navneson\", \"email\": \"navn@mail.com\", " +
                "\"password\": \"Passord123\" }"))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Transactional
  @Test
  void registerUserWithDuplicateEmailReturnsStatus400() throws Exception {
    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"displayName\": \"Navn Navnesen 2.0\", \"email\": \"navn@navnesen.no\", " +
                "\"password\": \"Passord1232.0\" }"))
        .andExpect(status().isBadRequest()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Transactional
  @Test
  void registerNewUserWithInvalidInputReturnsStatus400() throws Exception {
    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"displayName\": null, \"email\": \"navn@mail.com\", " +
                "\"password\": \"Passord123\" }"))
        .andExpect(status().isBadRequest()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"displayName\": \"Navn Navneson\", \"email\": null, " +
                "\"password\": \"Passord123\" }"))
        .andExpect(status().isBadRequest()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"displayName\": \"Navn Navneson\", \"email\": \"navn@mail.com\", " +
                "\"password\": null }"))
        .andExpect(status().isBadRequest()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"displayName\": \"Navn Navneson\", \"email\": \"navn@mail.com\", " +
                "\"test\": \"test\" }"))
        .andExpect(status().isBadRequest()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  //References the credentials for Navn Navnesen, with correct email and password.
  @Test
  void loginWithValidInputNavnNavnesenReturnsStatus200() throws Exception {
    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"navn@navnesen.no\", \"password\": \"Passord123\"}"))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void loginWithInvalidInputReturnsStatus400() throws Exception {
    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"test@mail.com\", \"password\": \"testForStatus\"}"))
        .andExpect(status().isBadRequest()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }
}

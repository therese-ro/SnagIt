package idatt2105.project.backend.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2105.project.backend.model.User;
import idatt2105.project.backend.util.TokenHandling;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

//The tests use the data in the data.sql file found in src/main/resources/sql and hard-code it in.
//It is not ideal, but the best we could to for tests with the time left.
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getInfoAboutSelfWhenAuthenticatedReturnsStatus200AndCorrectInfo() throws Exception {
    String token = TokenHandling.generateToken("3");

    MvcResult result = mockMvc.perform(get("/user/getInfo").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String json = result.getResponse().getContentAsString();
    User user = objectMapper.readValue(json, User.class);

    assertNotNull(user);
    //The returned user-id should always be 0 in this scenario.
    assertEquals(0, user.getUserId());
    assertEquals("Navn Navnesen", user.getDisplayName());
    assertEquals("navn@navnesen.no", user.getEmail());
  }

  @Test
  void getInfoAboutUserWhenUnauthorizedReturnsStatus403() throws Exception {
    mockMvc.perform(get("/user/getInfo"))
        .andExpect(status().isForbidden());
  }

  @Transactional
  @Test
  void updateEmailWithValidTokenReturnsStatus200AndCorrectlyUpdates() throws Exception {
    String token = TokenHandling.generateToken("3");

    mockMvc.perform(put("/user/updateEmail")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"navn@test.no\"}"))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    MvcResult result = mockMvc.perform(get("/user/getInfo").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String json = result.getResponse().getContentAsString();
    User user = objectMapper.readValue(json, User.class);

    assertEquals("navn@test.no", user.getEmail());
  }

  @Transactional
  @Test
  void updateEmailWhenUnauthorizedReturnsStatus403() throws Exception {
    mockMvc.perform(put("/user/updateEmail")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"navn@test.no\"}"))
        .andExpect(status().isForbidden());
  }

  @Transactional
  @Test
  void updateDisplayNameWithValidTokenReturnsStatus200AndCorrectlyUpdates() throws Exception {
    String token = TokenHandling.generateToken("3");

    mockMvc.perform(put("/user/updateDisplayName")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"displayName\": \"Navn 2.0\"}"))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    MvcResult result = mockMvc.perform(get("/user/getInfo").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String json = result.getResponse().getContentAsString();
    User user = objectMapper.readValue(json, User.class);

    assertEquals("Navn 2.0", user.getDisplayName());
  }

  @Transactional
  @Test
  void updateDisplayNameWhenUnauthorizedReturnsStatus403() throws Exception {
    mockMvc.perform(put("/user/updateDisplayName")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"displayName\": \"Navn 2.0\"}"))
        .andExpect(status().isForbidden());
  }

  @Transactional
  @Test
  void updatePasswordWithValidTokenReturnsStatus200() throws Exception {
    String token = TokenHandling.generateToken("3");

    mockMvc.perform(put("/user/updatePassword")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"oldPassword\": \"Passord123\", \"newPassword\": \"NewPassord123\", " +
                "\"newPasswordAgain\": \"NewPassord123\"}"))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Transactional
  @Test
  void updatePasswordWhenUnauthorizedReturnsStatus403() throws Exception {
    mockMvc.perform(put("/user/updatePassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"oldPassword\": \"Passord123\", \"newPassword\": \"NewPassord123\", " +
                "\"newPasswordAgain\": \"NewPassord123\"}"))
        .andExpect(status().isForbidden());
  }
}

package idatt2105.project.backend.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2105.project.backend.model.Category;
import idatt2105.project.backend.model.Listing;
import idatt2105.project.backend.model.ShortenedListing;
import idatt2105.project.backend.model.User;
import idatt2105.project.backend.util.TokenHandling;
import java.util.List;
import java.util.Map;
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
public class ListingControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getSingleListingReturnsStatus200AndCorrectAttributes() throws Exception {
    MvcResult
        result = mockMvc.perform(get("/listing/get/1"))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String json = result.getResponse().getContentAsString();
    Listing listing = objectMapper.readValue(json, Listing.class);

    String longDesc = "It has been in my family for 500 years, but it is finally time for it to get some new owners.";

    assertNotNull(listing);
    //The returned user-id should always be 0 in this scenario.
    assertEquals(1, listing.getItemID());
    assertEquals("Piggvar the Porcupine", listing.getShortDesc());
    assertEquals(longDesc, listing.getLongDesc());
    assertEquals(5000, listing.getPrice());
    assertEquals(9.551073155081776, listing.getLongitude());
    assertEquals(59.26876460303738, listing.getLatitude());
    assertEquals("piggvar.jpg", listing.getThumbnailPath());
    assertEquals(3, listing.getCategory().getCategoryID());
    assertEquals("Furniture", listing.getCategory().getCategoryName());
    assertEquals("Navn Navnesen", listing.getSellerDisplayName());
    assertEquals("navn@navnesen.no", listing.getSellerEmail());
    //isSeller should be false
    assertEquals(false, listing.getIsSeller());
  }

  @Test
  void getSingleListingWithAuthorizedNonOwnerReturnsStatus200AndCorrectAttributes() throws Exception {
    String token = TokenHandling.generateToken("2");

    MvcResult
        result = mockMvc.perform(get("/listing/get/1")
            .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String json = result.getResponse().getContentAsString();
    Listing listing = objectMapper.readValue(json, Listing.class);

    String longDesc = "It has been in my family for 500 years, but it is finally time for it to get some new owners.";

    assertNotNull(listing);
    //The returned user-id should always be 0 in this scenario.
    assertEquals(1, listing.getItemID());
    assertEquals("Piggvar the Porcupine", listing.getShortDesc());
    assertEquals(longDesc, listing.getLongDesc());
    assertEquals(5000, listing.getPrice());
    assertEquals(9.551073155081776, listing.getLongitude());
    assertEquals(59.26876460303738, listing.getLatitude());
    assertEquals("piggvar.jpg", listing.getThumbnailPath());
    assertEquals(3, listing.getCategory().getCategoryID());
    assertEquals("Furniture", listing.getCategory().getCategoryName());
    assertEquals("Navn Navnesen", listing.getSellerDisplayName());
    assertEquals("navn@navnesen.no", listing.getSellerEmail());
    //isSeller should be false
    assertEquals(false, listing.getIsSeller());
  }

  @Test
  void getSingleListingWithAuthorizedOwnerReturnsStatus200AndCorrectAttributes() throws Exception {
    String token = TokenHandling.generateToken("3");

    MvcResult
        result = mockMvc.perform(get("/listing/get/1")
            .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk()).andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String json = result.getResponse().getContentAsString();
    Listing listing = objectMapper.readValue(json, Listing.class);

    String longDesc = "It has been in my family for 500 years, but it is finally time for it to get some new owners.";

    assertNotNull(listing);
    //The returned user-id should always be 0 in this scenario.
    assertEquals(1, listing.getItemID());
    assertEquals("Piggvar the Porcupine", listing.getShortDesc());
    assertEquals(longDesc, listing.getLongDesc());
    assertEquals(5000, listing.getPrice());
    assertEquals(9.551073155081776, listing.getLongitude());
    assertEquals(59.26876460303738, listing.getLatitude());
    assertEquals("piggvar.jpg", listing.getThumbnailPath());
    assertEquals(3, listing.getCategory().getCategoryID());
    assertEquals("Furniture", listing.getCategory().getCategoryName());
    assertEquals("Navn Navnesen", listing.getSellerDisplayName());
    assertEquals("navn@navnesen.no", listing.getSellerEmail());
    //isSeller should now be true
    assertEquals(true, listing.getIsSeller());
  }

}

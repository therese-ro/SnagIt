package idatt2105.project.backend.controller;

import idatt2105.project.backend.model.Category;
import idatt2105.project.backend.model.Item;
import idatt2105.project.backend.model.Listing;
import idatt2105.project.backend.model.ShortenedListing;
import idatt2105.project.backend.model.User;
import idatt2105.project.backend.service.ImageService;
import idatt2105.project.backend.service.ItemService;
import idatt2105.project.backend.service.ListingService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/listing")
public class ListingController {

  @Autowired
  private ItemService itemService;
  @Autowired
  private ImageService imageService;
  @Autowired
  private ListingService listingService;

  @Operation(summary = "Add new listing",
      description = "Adds a new a listing to the database. The token will be used to get the userID," +
          "so this does not need to be sent with the request-body. The attribute for thumbnailPath" +
          " should not be empty or null. One should use the image/save endpoint that returns" +
          " the filename of the image before saving a new listing, and have this filename as the " +
          "thumbnailPath when trying to add a new listing.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(mediaType = "application/json",
          schema = @Schema(type = "object",
              example = "{\"shortDesc\": \"string\", \"longDesc\": \"string\", \"price\": 0, " +
                  "\"lontitude\": 9.0123, \"latitude\": 59.2130, \"thumbnailPath\": \"filename\"," +
                  "\"categoryID\": 1}"))))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
  @PostMapping("/addNew")
  public ResponseEntity<Object> addNewListing(@RequestBody Item item) {
    try {
      itemService.addNewListing(item);
      return new ResponseEntity<>(Map.of("status", "Successfully added new item!"),
          HttpStatus.OK);
    } catch (Exception e) {
      imageService.deleteImage(item.getThumbnailPath());
      return new ResponseEntity<>(Map.of("error",
          e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  };

  @Operation(summary = "Update listing",
      description = "Updates a specific listing. Checks will be made to ensure that" +
          " the authorized user is the owner of the item before attempting to update.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(mediaType = "application/json",
          schema = @Schema(type = "object",
              example = "{\"shortDesc\": \"string\", \"longDesc\": \"string\", \"price\": 0, " +
                  "\"lontitude\": 9.0123, \"latitude\": 59.2130, \"thumbnailPath\": \"filename\"," +
                  "\"isForSale\": false, \"categoryID\": 1}"))))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
  @PutMapping("/update")
  public ResponseEntity<Object> updateListing(@RequestBody Item item) {
    try {
      itemService.updateListing(item);
      return new ResponseEntity<>(Map.of("status", "Successfully updated item!"),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error",
          e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Delete listing",
      description = "Deletes a specific listing by its itemID. Checks will be made to ensure that" +
          " the authorized user is the owner of the item before attempting to delete.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
  @DeleteMapping("/delete/{itemID}")
  public ResponseEntity<Object> deleteListing(@PathVariable int itemID) {
    try {
      itemService.deleteListing(itemID);
      return new ResponseEntity<>(Map.of("status", "Successfully deleted item!"),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error",
          e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Get unfiltered listings",
      description = "Get all listings without a filter that are for sale. Will return the listings sorted by the " +
          "time they were created. Also uses pagination.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true, content = @Content(
          mediaType = "application/json",
          schema = @Schema(
              example = """
                {
                  "listings": [
                    { "itemID": 0, "shortDesc": "string", "price": 0, "longitude": 9.001, "latitude": 59.0123, "thumbnailPath": "picture.jpg" }
                  ],
                  "includesLastListing": true
                }
                """
          ))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @GetMapping("/get")
  public ResponseEntity<Object> getListOfListings(@RequestParam(name = "pagesize") int pagesize,
                                                  @RequestParam(name = "page") int page) {
    try {
      List<ShortenedListing> listings = listingService.getShortenedListingsPaginated(pagesize, page);
      Boolean includesLastListing = false;
      if (!listings.isEmpty()) {
        includesLastListing = listingService
            .checkIfListingPageIncludesLastListing(listings, pagesize, page);
      }
      return new ResponseEntity<>(Map.of("listings", listings,
          "includesLastListing", includesLastListing), HttpStatus.OK);    }
    catch (Exception e) {
      return new ResponseEntity<>(Map.of("error",
          e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Get information about a listing",
      description = "Get information about a listing by its itemID.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json",
          schema = @Schema(implementation = Listing.class))}),
      @ApiResponse(responseCode = "404", description = "Could not find listing", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @GetMapping("/get/{itemID}")
  public ResponseEntity<Object> getListing(@PathVariable int itemID) {
    try {
      Listing listing = listingService.getListingByID(itemID);
      if (listing == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(listing, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error",
          e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //Got help from ChatGPT on how to give an example of how a json-response will look.
  //Had to do this due to Swagger not working that well with Map.of().
  //This knowledge has been reused in the swagger documentations below as well.
  @Operation(summary = "Get listings from user",
      description = "Get all listings to a specific user. Extracts the id from the token in the " +
          "security context, so no additional information is required. Will return the listings sorted by the " +
          "time they were created. Also uses pagination.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true, content = @Content(
          mediaType = "application/json",
          schema = @Schema(
              example = """
                {
                  "listings": [
                     { "itemID": 0, "shortDesc": "string", "price": 0, "longitude": 9.001, "latitude": 59.0123, "thumbnailPath": "picture.jpg" }
                  ],
                  "includesLastListing": true
                }
                """
          ))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @GetMapping("/user")
  public ResponseEntity<Object> getUserListing(@RequestParam(name = "pagesize") int pagesize,
                                               @RequestParam(name = "page") int page) {
    try {
      List<ShortenedListing> listings = listingService.getListingsFromUserPaginated(pagesize, page);
      Boolean includesLastListing = false;
      if(!listings.isEmpty()) {
        includesLastListing = listingService.checkIfUserListingsPageIncludesLastListing(listings, pagesize, page);
      }
      return new ResponseEntity<>(Map.of("listings", listings,
          "includesLastListing", includesLastListing), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error",
          e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Get listings by category",
      description = "Get all listings from a specific category that are for sale. Will return the " +
          "listings sorted by the time they were created. Also uses pagination.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(
          mediaType = "application/json",
          schema = @Schema(
              example = """
                {
                  "listings": [
                     { "itemID": 0, "shortDesc": "string", "price": 0, "longitude": 9.001, "latitude": 59.0123, "thumbnailPath": "picture.jpg" }
                  ],
                  "includesLastListing": true
                }
                """
          ))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @GetMapping("/category/{categID}")
  public ResponseEntity<Object> getCategoryListing(@PathVariable int categID,
                                                   @RequestParam(name = "pagesize") int pagesize,
                                                   @RequestParam(name = "page") int page) {
    try {
      List<ShortenedListing> listings =
          listingService.getShortenedListingsFromCategoryPaginated(categID, pagesize, page);

      Boolean includesLastListing = false;
      if(!listings.isEmpty()) {
        includesLastListing = listingService
            .checkIfCategoryListingPageIncludesLastListing(categID, listings, pagesize, page);
      }

      return new ResponseEntity<>(Map.of("listings", listings,
          "includesLastListing", includesLastListing), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error",
          e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

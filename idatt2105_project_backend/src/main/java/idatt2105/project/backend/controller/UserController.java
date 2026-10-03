package idatt2105.project.backend.controller;

import idatt2105.project.backend.model.User;
import idatt2105.project.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @Operation(summary = "Get information about user.",
  description = "Get information about user including email and display name. ID will always be 0.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json",
          schema = @Schema(implementation = User.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
  @GetMapping("/getInfo")
  public ResponseEntity<Object> getPersonalInfo() {
    try {
      User user = userService.getPersonalInfo();
      if (user == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error", e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Update email",
      description = "Update an existing user's email.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(mediaType = "application/json",
          schema = @Schema(type = "object",
              example = "{\"email\": \"test@mail.no\"}"))))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @PutMapping("/updateEmail")
  public ResponseEntity<Object> updateEmail(@RequestBody Map<String, String> emailUpdateData) {
    try {
      String email = emailUpdateData.get("email");
      userService.updateEmail(email);
      return new ResponseEntity<>(Map.of("status", "Successfully updated email!"),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error", e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Update display name",
      description = "Update an existing user's display name.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(mediaType = "application/json",
          schema = @Schema(type = "object",
              example = "{\"displayName\": \"Oscar Wilde\"}"))))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @PutMapping("/updateDisplayName")
  public ResponseEntity<Object> updateDisplayName(
      @RequestBody Map<String, String> displayNameUpdateData) {
    try {
      String displayName = displayNameUpdateData.get("displayName");
      userService.updateDisplayName(displayName);
      return new ResponseEntity<>(Map.of("status", "Successfully updated display name!"),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error", e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //Got help from ChatGPT with the use of requestbody inside @Operation to improve swagger documentation
  @Operation(summary = "Update password",
      description = "Update an existing user's password.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(mediaType = "application/json",
          schema = @Schema(type = "object",
              example = "{\"oldPassword\": \"password123\", \"newPassword\": \"newPassword123\", \"newPasswordAgain\": \"newPassword123\"}"))))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @PutMapping("/updatePassword")
  public ResponseEntity<Object> updatePassword(
      @RequestBody Map<String, String> passwordUpdateData) {
    try {
      String oldPassword = passwordUpdateData.get("oldPassword");
      String newPassword = passwordUpdateData.get("newPassword");
      String newPasswordAgain = passwordUpdateData.get("newPasswordAgain");
      userService.updatePassword(oldPassword, newPassword, newPasswordAgain);
      return new ResponseEntity<>(Map.of("status", "Successfully updated password!"),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error", e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

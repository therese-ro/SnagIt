package idatt2105.project.backend.controller;

import idatt2105.project.backend.model.UserCredentials;
import idatt2105.project.backend.model.UserRegistration;
import idatt2105.project.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Operation(summary = "Login for user",
      description = "Attempt to login with user-credentials")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
      @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
  @PostMapping("/login")
  public ResponseEntity<Object> attemptLogin(@RequestBody UserCredentials userCredentials) {
    try {
      String token = authService.login(userCredentials);
      if(token.isEmpty()) {
        return new ResponseEntity<>(Map.of("status", "Invalid credentials."),
            HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(Map.of("status", "Successfully logged in!",
          "token", token), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error", e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Register a user",
      description = "Attempt to register a user with email, display-name and password.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
      @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = @Content)})
  @PostMapping("/register")
  public ResponseEntity<Object> attemptRegistration(@RequestBody UserRegistration userRegistration) {
    try {
      String token = authService.registerUser(userRegistration);
      if (token.isEmpty()) {
        return new ResponseEntity<>(Map.of("status", "Invalid credentials."),
            HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(Map.of("status", "Successfully registered new user.",
          "token", token), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Map.of("error", e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

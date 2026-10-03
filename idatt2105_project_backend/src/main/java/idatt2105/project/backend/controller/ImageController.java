package idatt2105.project.backend.controller;

import idatt2105.project.backend.service.ImageService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//The code below was made with the help from this website:
//https://medium.com/@dulanjayasandaruwan1998/uploading-images-in-a-spring-boot-project-a-step-by-step-guide-8a55248ea520
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/image")
public class ImageController {

  @Autowired
  private ImageService imageService;

  @PostMapping("/save")
  public ResponseEntity<Object> addPictureForListing(@RequestParam("file") MultipartFile file) {
    try{
      String filename = imageService.saveImage(file);
      return new ResponseEntity<>(Map.of("status", "Image uploaded successfully!",
          "filename", filename), HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>(Map.of("error",
          "An error occurred when uploading the image."), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(Map.of("error",
          e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/{filename}")
  public ResponseEntity<Resource> getImage(@PathVariable String filename) {
    try {
      Resource resource = imageService.getImage(filename);
      String filetype = filename.substring(filename.lastIndexOf(".") + 1);

      if (filetype.equalsIgnoreCase("jpeg") ||
          filetype.equalsIgnoreCase("jpg")) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
      } else if (filetype.equalsIgnoreCase("png")) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
      }

      throw new RuntimeException();
    } catch (MalformedURLException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}

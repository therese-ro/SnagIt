package idatt2105.project.backend.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//code inspired by https://medium.com/@dulanjayasandaruwan1998/uploading-images-in-a-spring-boot-project-a-step-by-step-guide-8a55248ea520
//and made som adjustments to fit with our application and functionality
@Service
public class ImageService {

  private final String directory = "filesystem/uploads";

  public String saveImage(MultipartFile file) throws IOException,
      RuntimeException {
    validateImageFile(file);

    Path uploadPath = Paths.get(directory);

    //ensures that a folder will be made if it has not already been
    if(!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    String fileName = LocalDateTime.now().toString() + file.getOriginalFilename();
    Path fileUploadPath = Paths.get(directory + "/" + fileName);

    if (fileUploadPath.toFile().exists()) {
      throw new RuntimeException("Failed to save image due to filename-conflict.");
    }

    Files.copy(file.getInputStream(), fileUploadPath);
    return fileName;
  }

  public Resource getImage(String filename) throws MalformedURLException, RuntimeException {
    Path filePath = Paths.get(directory).resolve(filename);
    Resource resource = new UrlResource(filePath.toUri());

    if (resource.exists()) {
      return resource;
    } else {
      throw new RuntimeException("Could not find the file.");
    }
  }

  public void deleteImage(String filename) {
    try {
      Path filePath = Paths.get(directory).resolve(filename);
      Files.deleteIfExists(filePath);
    } catch (IOException | InvalidPathException e) {
      //Change to logging in the future.
      System.out.println("Could not delete the requested image.");
    }
  }

  private void validateImageFile(MultipartFile file) throws IllegalArgumentException{
    if (file == null) {
      throw new IllegalArgumentException("No file was received.");
    }
    String contentType = file.getContentType();
    if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
      throw new IllegalArgumentException("The file sent was not a jpeg- or png-image.");
    }
  }
}

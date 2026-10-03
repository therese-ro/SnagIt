package idatt2105.project.backend.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Code inspired by: https://bell-sw.com/blog/documenting-rest-api-with-swagger-in-spring-boot-3/#mcetoc_1heq9ft3o1g
@Configuration
public class OpenAPIConfiguration {

  @Bean
  public OpenAPI defineOpenApi() {
    Info information = new Info();
    information.title("SnagIt Server API")
        .description("This API exposes endpoints in use for the SnagIt-web-application.");

    return new OpenAPI().info(information);
  }
}

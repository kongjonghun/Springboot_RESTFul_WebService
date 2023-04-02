package LGCNS.RestfulWebService.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import org.springdoc.core.models.GroupedOpenApi;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2
@OpenAPIDefinition
public class SwaggerConfig {

//    // localhost:8088/v2/api-docs
//    @Bean
//    public Docket DocumentApi(){
//        return new Docket(DocumentationType.SWAGGER_2);
//    }

    @Bean
    public OpenAPI api(){
        Info info = new Info().title("").version("v1").description("aaa");

        return new OpenAPI().components(new Components()).info(info);
    }

}

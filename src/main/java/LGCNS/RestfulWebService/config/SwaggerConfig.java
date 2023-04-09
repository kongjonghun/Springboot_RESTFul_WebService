package LGCNS.RestfulWebService.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import org.springdoc.core.models.GroupedOpenApi;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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

    // CONTACT
    private static final Contact DEFAULT_CONTACT = new Contact().name("Kenneth Lee").email("edowon@joneconsulting.co.kr").url("http://www.joneconsulting.co.kr");

    private static final License license = new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0");

    // API INFO
    private static final Info DEFAULT_API_INFO = new Info().title("Awesome API Title")
            .description("My User management REST API Service")
            .version("1.0")
            .termsOfService("urn:tos")
            .contact(DEFAULT_CONTACT).license(license);


//    // PRODUCES AND CONSUMES  --> application.yml
//    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
//            Arrays.asList("application/json", "application/xml"));

    @Bean
    public OpenAPI api(){
        return new OpenAPI().components(new Components())
                .info(DEFAULT_API_INFO);

    }

}

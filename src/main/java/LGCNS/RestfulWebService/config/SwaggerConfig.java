package LGCNS.RestfulWebService.config;


import io.swagger.models.License;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // CONTACT
    private static final Contact DEFAULT_CONTACT = new Contact("Kenneth Lee", "http://www.joneconsulting.co.kr","edowon@joneconsulting.co.kr" );


    // API INFO
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Awesome API Title",
            "My User management REST API Service",
            "1.0",
            "urn:tos",
            DEFAULT_CONTACT,
            "Apache License Version 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList<>());


    // PRODUCES AND CONSUMES  --> application.yml
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json", "application/xml"));

    // localhost:8088/v2/api-docs
    @Bean
    public Docket DocumentApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }

//    @Bean
//    public OpenAPI api(@Value("${springdoc.version}") String springdocVersion){
//        Info info = new Info().title("타이틀 입력").version(springdocVersion).description("API에 대한 설명");
//
//        return new OpenAPI().components(new Components())
//                .info(info);
//    }
}

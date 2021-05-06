package org.demo.todo.todoapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
@EnableOpenApi
public class AppConfiguration {

    //
    @Bean
    public Docket api() {
        Server serverLocal = new Server("local", "http://localhost:8080", "for local usages", Collections.emptyList(), Collections.emptyList());

        return new Docket(DocumentationType.OAS_30)
                .servers(serverLocal)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tutorial.codefirst"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Openapi OAS3 with springfox ")
                .description("Code first approach")
                .version("1.0.0")
                .contact(new Contact("Marone", "https://wstutorial.com", "test@wstutorial.com"))
                .build();
    }

}
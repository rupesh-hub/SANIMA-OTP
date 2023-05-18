package com.infodev.sanimaotp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(){
       return new Docket(DocumentationType.SWAGGER_2)
               .select()
               .apis(RequestHandlerSelectors.basePackage("com.infodev.sanimaotp.controller.ApiController"))
               .paths(PathSelectors.any())
               .build()
               .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "OTP API documentation",
                "API documentation for integrating OTP solution for Sanima Bank. <br>" +
                        "<br>1. Get serial key, activation code and  for activating digipass mobile app<br>" +
                        "<br>2. Activate serial key for mobile application with derivation code<br>" +
                        "<br>3. Validate generated OTP<br> " +
                        "<br>4. Generate challenge in case of Challenge Response mode<br> ",
                "API version 0.1",
                "",
                new Contact("", "", ""),
                "", "", Collections.emptyList());
    }
}

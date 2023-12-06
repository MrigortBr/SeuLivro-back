package com.igortbr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI().info
        		(new Info()
        		.title("API De Livros")
                .description("Descrição da API")
                .version("0.0.1-SNAPSHOT")
                .termsOfService("Non-enable")
                .license(new License().name("Open Source").url("www.github.com"))
                .contact(new Contact().email("igorperecom@gmail.com").name("MrIgortBr").url("git"))
        		);
    }

}


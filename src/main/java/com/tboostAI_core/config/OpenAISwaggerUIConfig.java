package com.tboostAI_core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        security = @SecurityRequirement(name = "Authorization")
)
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "Authorization", scheme = "Authorization", in = SecuritySchemeIn.HEADER)
@Configuration
public class OpenAISwaggerUIConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        String title = "SpringDoc API";
        String description = "SpringDoc Application";
        String version = "v0.0.1";
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version));
    }
}
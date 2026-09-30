package org.example.config;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI digitalHealthOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Digital Health API")
                        .version("1.0")
                        .description(
                                "REST API for managing patients, encounters, " +
                                        "and clinical observations."
                        ))
                .components(new Components()
                        .addSecuritySchemes(
                                "apiKey",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("X-API-Key")
                        ));
    }
}

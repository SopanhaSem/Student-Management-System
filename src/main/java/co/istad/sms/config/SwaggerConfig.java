package co.istad.sms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${sms.openapi.dev-url}")
    private String devUrl;

    @Value("${sms.openapi.stage-url}")
    private String stageUrl;

    @Value("${sms.openapi.prod-url}")
    private String prodUrl;

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development environment server");

        Server stageServer = new Server();
        stageServer.setUrl(stageUrl);
        stageServer.setDescription("Testing environment server");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Production environment server");

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication")
                )
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("SMS Service API")
                        .description("API documentation for the SMS Service")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Sem Sopanha")
                                .email("semsopanha072@gmail.com")
                                .url("https://sem-sopanha.vercel.app")
                        )
                        .license(new License().name("License of API")
                                .url("API license URL")
                        )
                )
                .servers(List.of(devServer, stageServer, prodServer));
    }
}

package com.github.alextheartisan.boox.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("documents").pathsToMatch("/documents/**").build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        var info = new Info().title("Boox API").version("version").description("description");

        var servers = List.of(new Server().url("http://localhost:7001").description("Dev service"));

        return new OpenAPI().info(info).servers(servers);
    }
}

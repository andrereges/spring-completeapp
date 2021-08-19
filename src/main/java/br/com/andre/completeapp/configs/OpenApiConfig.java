package br.com.andre.completeapp.configs;

import br.com.andre.completeapp.utils.MavenPomPropertyUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.maven.model.Model;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final Model pomModel = MavenPomPropertyUtil.getPom();

    @Bean
    public GroupedOpenApi storeOpenApi() {
        String paths[] = {"/api/v1/**"};

        String packagesToscan[] = {
            "br.com.andre.completeapp.controllers",
            "br.com.andre.completeapp.dtos"
        };

        return GroupedOpenApi.builder()
                .group(pomModel.getName().concat(" API Version 1"))
                .pathsToMatch(paths)
                .packagesToScan(packagesToscan)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(pomModel.getName())
                .version(pomModel.getVersion())
                .description(pomModel.getDescription())
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                .url(pomModel.getUrl()))
            );
    }
}

package cz.mendelu.best_scorers_stats.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


        // OpenAPI configuration
        // localhost:8090/swagger-ui/index.html
        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("Football Scorers Stats")
                                .description("Football scorers stats application from year 2016-2022.")
                                .version("1.0")
                        );
        }
}

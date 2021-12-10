package shop.heartmuscle.heartmuscle.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("shop.heartmuscle.mytrip")
                .pathsToMatch("/**")
                .packagesToScan("shop.heartmuscle.heartmuscle.controller")
                .build();

    }
}

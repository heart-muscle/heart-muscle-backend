package shop.heartmuscle.heartmuscle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342", "http://127.0.0.1:8080", "https://d2lushdgt6wy3k.cloudfront.net", "https://www.heartmuscle.shop", "http://www.heartmuscle.shop", "www.heartmuscle.shop")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "HEAD");

    }
}

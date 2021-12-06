package shop.heartmuscle.heartmuscle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HeartMuscleBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartMuscleBackendApplication.class, args);
    }

}

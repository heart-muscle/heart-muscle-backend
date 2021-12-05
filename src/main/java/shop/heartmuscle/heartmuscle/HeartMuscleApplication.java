package shop.heartmuscle.heartmuscle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HeartMuscleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartMuscleApplication.class, args);
    }

}

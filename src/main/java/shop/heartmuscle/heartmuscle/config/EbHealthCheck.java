package shop.heartmuscle.heartmuscle.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbHealthCheck {

    // EB 헬스체크를 위한 코드
    @GetMapping( "/")
    public ResponseEntity<?> doHealthCheck() {
        return ResponseEntity.ok("HealthCheck OK");
    }
}
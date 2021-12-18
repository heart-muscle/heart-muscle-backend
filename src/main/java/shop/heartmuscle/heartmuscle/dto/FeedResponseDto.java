package shop.heartmuscle.heartmuscle.dto;

import lombok.Getter;
import lombok.Setter;
import shop.heartmuscle.heartmuscle.domain.User;

import java.util.Set;

public class FeedResponseDto {

    @Getter
    @Setter
    public static class Response {

        private Long id;
        private String title;
        private String content;
        private User user;
        private Set tags;
        private String imageUrl;
    }
}

package shop.heartmuscle.heartmuscle.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserResponseDto {

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String username;
        private String nickname;
        private String imgUrl;
        private List feeds;
    }
}

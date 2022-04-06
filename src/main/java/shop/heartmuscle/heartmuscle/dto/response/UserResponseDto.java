package shop.heartmuscle.heartmuscle.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
        private Long id;
        private String username;
        private String nickname;
        private String imgUrl;
        private List feeds;
}

package shop.heartmuscle.heartmuscle.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@RequiredArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private MultipartFile imgUrl;
}
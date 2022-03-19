package shop.heartmuscle.heartmuscle.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@RequiredArgsConstructor
public class FeedRequestDto {
    private String title;
    private String content;
    private String tags;
    private String username;
    private MultipartFile image;
}
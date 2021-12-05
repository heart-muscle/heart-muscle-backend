package shop.heartmuscle.heartmuscle.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class FeedRequestDto {
    private final String title;
    private final String content;
}

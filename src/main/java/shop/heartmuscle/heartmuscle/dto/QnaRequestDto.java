package shop.heartmuscle.heartmuscle.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class QnaRequestDto {
    private String username;
    private final String title;
    private final String content;
}

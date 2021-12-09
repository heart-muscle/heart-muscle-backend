package shop.heartmuscle.heartmuscle.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class QnaCommentRequestDto {
    private Long id;
    private String username;
    private String comment;
}

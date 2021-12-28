package shop.heartmuscle.heartmuscle.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class QnaCommentRequestDto {
    private String comment;

    public QnaCommentRequestDto(String comment) {
        this.comment = comment;
    }
}

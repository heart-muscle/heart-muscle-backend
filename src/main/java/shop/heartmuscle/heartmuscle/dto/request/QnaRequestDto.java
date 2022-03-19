package shop.heartmuscle.heartmuscle.dto.request;

import lombok.*;
import shop.heartmuscle.heartmuscle.entity.Qna;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaRequestDto {
    private String title;
    private String content;

    public QnaRequestDto(Qna qna) {
        this.title = qna.getTitle();
        this.content = qna.getContent();
    }

    public static Qna toQna(QnaRequestDto qnaRequestDto) {
        return Qna.builder()
                .title(qnaRequestDto.getTitle())
                .content(qnaRequestDto.getContent())
                .build();

    }

}
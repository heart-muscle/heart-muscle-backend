package shop.heartmuscle.heartmuscle.dto.response;

import lombok.*;
import shop.heartmuscle.heartmuscle.entity.Qna;
import shop.heartmuscle.heartmuscle.entity.QnaComment;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private String nickname;
    private List<QnaComment> comment;

    public QnaResponseDto(final Qna qna) {
        this.title = qna.getTitle();
        this.content = qna.getContent();
        this.id = qna.getId();
        this.username = qna.getUser().getUsername();
        this.nickname = qna.getUser().getNickname();
        this.comment = qna.getComment();
    }

    public static QnaResponseDto toDto(final Qna qna) {
        return QnaResponseDto.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .comment(qna.getComment())
                .nickname(qna.getUser().getNickname())
                .username(qna.getUser().getUsername())
                .build();

    }
}
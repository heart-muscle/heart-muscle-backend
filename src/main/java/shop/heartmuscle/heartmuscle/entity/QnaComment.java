package shop.heartmuscle.heartmuscle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import shop.heartmuscle.heartmuscle.dto.request.QnaCommentRequestDto;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class QnaComment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
//    @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="qna_id", nullable = false)
    private Qna qna;

//    @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public QnaComment(QnaCommentRequestDto requestDto, Qna qna, User user) {
        this.comment = requestDto.getComment();
        this.qna = qna;
        this.user = user;
    }

    public void update(String comment) {
        this.comment = comment;
    }

}
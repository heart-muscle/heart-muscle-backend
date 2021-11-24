package com.example.qna.domain;

import com.example.qna.dto.QnaCommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class QnaComment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long idx;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="qna_idx", nullable = false)
    private Qna qna;

    public QnaComment(QnaCommentRequestDto requestDto, Qna qna) {
        this.comment = requestDto.getComment();
        this.qna = qna;
    }

}

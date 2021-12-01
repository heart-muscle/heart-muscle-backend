package com.example.qna.domain;

import com.example.qna.dto.QnaCommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class QnaComment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="qna_id", nullable = false)
    private Qna qna;

    public QnaComment(QnaCommentRequestDto requestDto, Qna qna) {
        this.comment = requestDto.getComment();
        this.qna = qna;
    }

}
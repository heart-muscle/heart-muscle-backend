package com.example.qna.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QnaCommentRequestDto {
    private Long idx;
    private String comment;
}
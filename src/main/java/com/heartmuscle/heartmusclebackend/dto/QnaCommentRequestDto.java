package com.heartmuscle.heartmusclebackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Getter
public class QnaCommentRequestDto {
    private Long id;
    private String comment;
}
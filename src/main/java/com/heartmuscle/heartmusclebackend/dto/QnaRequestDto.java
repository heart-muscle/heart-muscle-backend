package com.heartmuscle.heartmusclebackend.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QnaRequestDto {
    private final String title;
    private final String content;
}

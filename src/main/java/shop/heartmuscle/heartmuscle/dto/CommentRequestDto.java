package shop.heartmuscle.heartmuscle.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CommentRequestDto {
    private final Long id;
    private final String comment;
}

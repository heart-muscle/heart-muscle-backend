package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.heartmuscle.heartmuscle.dto.request.QnaCommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.ResultResponseDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.QnaCommentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class QnaCommentController {
    private final QnaCommentService qnaCommentService;

    @Operation(description = "댓글 작성, 로그인 필요", method = "POST")
    @PostMapping("/qna/{qnaId}/comment")
    public void createQnaComment(@PathVariable Long qnaId,
                              @RequestBody QnaCommentRequestDto qnaCommentRequestDto,
                              @AuthenticationPrincipal UserDetailsImpl nowUser){

        qnaCommentService.createQnaComment(qnaId, qnaCommentRequestDto, nowUser);
    }


    @Operation(description = "댓글 수정, 로그인 필요", method = "PUT")
    @PutMapping("/qna/{qnaId}/comments/{qnaCommentId}") // 댓글 수정하기
    public ResultResponseDto updateQnaComment(@PathVariable Long qnaId,
                                              @PathVariable Long qnaCommentId,
                                              @RequestBody QnaCommentRequestDto qnaCommentRequestDto,
                                              @AuthenticationPrincipal UserDetailsImpl nowUser) {

        return qnaCommentService.updateQnaComment(qnaId, qnaCommentId, qnaCommentRequestDto, nowUser);
    }

    @Operation(description = "댓글 삭제, 로그인 필요", method = "DELETE")
    @DeleteMapping("/qna/{qnaId}/comments/{qnaCommentId}") // 댓글 삭제하기
    public ResultResponseDto deleteQnaComment(@PathVariable Long qnaCommentId,
                                              @PathVariable Long qnaId,
                                              @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return qnaCommentService.deleteQnaComment(qnaId, qnaCommentId, nowUser);
    }
}


package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.heartmuscle.heartmuscle.domain.QnaComment;
import shop.heartmuscle.heartmuscle.dto.QnaCommentRequestDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.QnaCommentService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

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
    public QnaComment updateQnaComment(@PathVariable Long qnaId,
                                       @PathVariable Long qnaCommentId,
                                       @RequestBody QnaCommentRequestDto qnaCommentRequestDto,
                                       @AuthenticationPrincipal UserDetailsImpl nowUser) {

        return qnaCommentService.updateQnaComment(qnaId, qnaCommentId, qnaCommentRequestDto, nowUser);
    }

    @Operation(description = "댓글 삭제, 로그인 필요", method = "DELETE")
    @DeleteMapping("/qna/{qnaId}/comments/{qnaCommentId}") // 댓글 삭제하기
    public ResponseEntity<?> deleteQnaComment(@PathVariable Long qnaCommentId,
                                              @PathVariable Long qnaId,
                                              @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return qnaCommentService.deleteQnaComment(qnaId, qnaCommentId, nowUser);
    }
}


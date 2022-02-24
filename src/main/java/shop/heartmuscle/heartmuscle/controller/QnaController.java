package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.dto.QnaRequestDto;
import shop.heartmuscle.heartmuscle.dto.ResultResponseDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.qna.QnaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class QnaController {

    private final QnaServiceImpl qnaService;

    @PostMapping("/qna")
    public ResponseEntity<?> createQna(@RequestBody QnaRequestDto qnaRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {


        Qna qnaEntity = QnaRequestDto.toQna(qnaRequestDto);

        qnaEntity.setUser(nowUser.getUser());

        List<Qna> entities = qnaService.createQna(qnaEntity);



        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/qna")
    public Page<Qna> getQna(@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy,
                            @RequestParam("isAsc") boolean isAsc){
        page = page - 1;
        return qnaService.getQna(page , size, sortBy, isAsc);
    }


    @GetMapping("/qna/{id}")
    public Qna getQna(@PathVariable Long id){
        return qnaService.getQna(id);
    }

    @Operation(description = "게시글 수정, 로그인 필요", method = "PUT")
    @PutMapping("/qna/{id}")
    public ResultResponseDto updateQna(@PathVariable Long id,
                                       @RequestBody QnaRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return qnaService.update(id, requestDto, nowUser);
    }

    @Operation(description = "게시글 삭제, 로그인 필요", method = "DELETE")
    @DeleteMapping("/qna/{id}")
    public ResultResponseDto deleteQna(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return qnaService.delete(id, nowUser);
    }

}
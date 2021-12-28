package shop.heartmuscle.heartmuscle.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.dto.QnaRequestDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/qna")
    public Qna setQna(@RequestBody QnaRequestDto qnaRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return qnaService.setQna(qnaRequestDto, nowUser);
    }

    @GetMapping("/qna")
    public Page<Qna> getQna(@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy,
                            @RequestParam("isAsc") boolean isAsc){
        System.out.println(page);
        System.out.println(size);
        System.out.println(sortBy);
        System.out.println(isAsc);
        page = page - 1;
        return qnaService.getQna(page , size, sortBy, isAsc);
    }


    @GetMapping("/qna/{id}")
    public Qna getQna(@PathVariable Long id){
        return qnaService.getQna(id);
    }

    @PutMapping("/qna/{id}")
    public Long updateQna(@PathVariable Long id, @RequestBody QnaRequestDto requestDto) {
        return qnaService.update(id, requestDto);
    }

    @DeleteMapping("/qna/{id}")
    public Long deleteLecture(@PathVariable Long id) {
        return qnaService.delete(id);
    }

}
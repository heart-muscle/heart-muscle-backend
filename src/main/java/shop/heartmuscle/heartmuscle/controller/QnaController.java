package shop.heartmuscle.heartmuscle.controller;

import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.dto.QnaRequestDto;
import shop.heartmuscle.heartmuscle.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/qna")
    public Qna setQna(@RequestBody QnaRequestDto qnaRequestDto){
        return qnaService.setQna(qnaRequestDto);
    }

    @GetMapping("/qna")
    public List<Qna> getQna(){
        return qnaService.getQna();
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
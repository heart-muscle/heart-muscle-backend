package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import shop.heartmuscle.heartmuscle.controller.modelassembler.QnaModelAssembler;
import shop.heartmuscle.heartmuscle.entity.Qna;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.dto.response.ResultResponseDto;
import shop.heartmuscle.heartmuscle.dto.response.QnaResponseDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.qna.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
public class QnaController {

    private final QnaService qnaService;
    private final QnaModelAssembler qnaModelAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    @PostMapping("/qna")
    public ResponseEntity<?> createQna(@RequestBody QnaRequestDto qnaRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) {


        Qna qnaEntity = QnaRequestDto.toQna(qnaRequestDto);

        qnaEntity.setUser(nowUser.getUser());

        Qna qna = qnaService.createQna(qnaEntity);

        QnaResponseDto qnaResponseDto = new QnaResponseDto(qna);

        EntityModel<QnaResponseDto> entityModel = qnaModelAssembler.toModel(qnaResponseDto);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/qna")
    public HttpEntity<PagedModel<?>> getQnaAll(Pageable pageable){

        Page<QnaResponseDto> qnaResponseDto = qnaService.getQnaAll(pageable).map(QnaResponseDto::toDto);

        return ResponseEntity.ok().contentType(MediaTypes.HAL_JSON).body(pagedResourcesAssembler.toModel(qnaResponseDto, qnaModelAssembler));
    }

    @GetMapping("/qna/{id}")
    public EntityModel<QnaResponseDto> getQnaOne(@PathVariable Long id){

        Qna qna = qnaService.getQnaOne(id);

        QnaResponseDto qnaResponseDto = QnaResponseDto.toDto(qna);

        return qnaModelAssembler.toModel(qnaResponseDto);
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
package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import shop.heartmuscle.heartmuscle.controller.modelassembler.QnaModelAssembler;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.dto.ResultResponseDto;
import shop.heartmuscle.heartmuscle.dto.response.QnaResponseDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.qna.QnaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
public class QnaController {

    private final QnaServiceImpl qnaService;
    private final QnaModelAssembler qnaModelAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

//    @PostMapping("/qna")
//    public ResponseEntity<?> createQna(@RequestBody QnaRequestDto qnaRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
//
//
//        Qna qnaEntity = QnaRequestDto.toQna(qnaRequestDto);
//
//        qnaEntity.setUser(nowUser.getUser());
//
//        Qna entities = qnaService.createQna(qnaEntity);
//
//
//
//        return new ResponseEntity<>(entities, HttpStatus.OK);
//    }

    @PostMapping("/qna")
    public ResponseEntity<?> createQna(@RequestBody QnaRequestDto qnaRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {


        Qna qnaEntity = QnaRequestDto.toQna(qnaRequestDto);

        qnaEntity.setUser(nowUser.getUser());
//        List<Qna> qna = qnaService.createQna(qnaEntity);

//        Qna collect1 = qna.stream().map(Qna::new).collect(Collectors.toList());

        System.out.println("qnaEntity = " + qnaEntity.getUser().getUsername());
        Qna qna = qnaService.createQna(qnaEntity);

        QnaResponseDto qnaResponseDto = new QnaResponseDto(qna);

//        QnaResponseDto map1 = modelMapper.map(qnaService.createQna(qnaEntity), QnaResponseDto.class);
//        EntityModel<Qna> entityModel = qnaPostModelAssembler.toModel(nn);
//        System.out.println("클래스 확인" + nn.getClass());

        EntityModel<QnaResponseDto> entityModel = qnaModelAssembler.toModel(qnaResponseDto);

////        R collect = qna.stream().map(Qna::valueOf).collect(Collectors.joining());
//
//        System.out.println("qna.toArray() = " + qna.toArray());
//
//        System.out.println("qnaEntity = " + qnaEntity.toString());
//        System.out.println("qnaEntity = " + qnaEntity.getId());
//
////        EntityModel<Qna> entityModel = qnaPostModelAssembler.toModel(nn);
////        System.out.println("entityModel.toString() = " + entityModel.toString());
//
//        QnaResponseDto map = modelMapper.map(qna, QnaResponseDto.class);
//        System.out.println("map.getId() = " + map.getId());
//
//        EntityModel<QnaResponseDto> entityModel = qnaModelAssembler.toModel(map);
//        System.out.println("entityModel.getId() = " + entityModel.getId());


        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
//        return ResponseEntity.ok(qna);
    }

//    @GetMapping("/qna")
//    public Page<Qna> getQnaAll(@RequestParam(value = "page") int page,
//                            @RequestParam(value = "size") int size,
//                            @RequestParam(value = "sortBy") String sortBy,
//                            @RequestParam(value = "isAsc") boolean isAsc){
//        page = page - 1;
//        return qnaService.getQnaAll(page , size, sortBy, isAsc);
//    }

    @GetMapping("/qna")
    public HttpEntity<PagedModel<?>> getQnaAll(Pageable pageable){
        Page<Qna> qnaEntity = qnaService.getQnaAll(pageable);

        Page<QnaResponseDto> qnaResponseDto = qnaService.getQnaAll(pageable).map(QnaResponseDto::toDto);

//        Page<QnaResponseDto> qnaResponseDto = qnaEntity.map(new Converter<qnaEntity, qnaResponseDto>() {
//            @Override
//            public QnaResponseDto convert(Qna qna) {
//                QnaResponseDto qnaResponseDto1 = new QnaResponseDto();
//
//            }
//        })
//
//        Page<QnaResponseDto> qnaResponseDto = qnaEntity.stream().map(QnaResponseDto::new).collect(Collectors.to());


//        return new ResponseEntity<>(assembler.toModel(qnaResponseDto), HttpStatus.OK);
//        return new ResponseEntity<>(assembler.toModel(qnaEntity), HttpStatus.OK);
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
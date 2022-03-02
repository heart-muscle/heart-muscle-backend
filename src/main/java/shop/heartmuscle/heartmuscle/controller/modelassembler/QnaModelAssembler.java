package shop.heartmuscle.heartmuscle.controller.modelassembler;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import shop.heartmuscle.heartmuscle.controller.QnaController;
import shop.heartmuscle.heartmuscle.dto.response.QnaResponseDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QnaModelAssembler implements RepresentationModelAssembler<QnaResponseDto, EntityModel<QnaResponseDto>> {

    @Override
    public EntityModel<QnaResponseDto> toModel(QnaResponseDto qnaResponseDto) {

        return EntityModel.of(qnaResponseDto, linkTo(methodOn(QnaController.class).getQnaOne(qnaResponseDto.getId())).withSelfRel(),
                linkTo(methodOn(QnaController.class).getQnaAll(Pageable.unpaged())).withRel("qna"));
    }
}

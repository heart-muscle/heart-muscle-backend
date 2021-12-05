package shop.heartmuscle.heartmuscle.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.domain.Comment;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.Image;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.ImageRequestDto;
import shop.heartmuscle.heartmuscle.repository.CommentRepository;
import shop.heartmuscle.heartmuscle.repository.ImageRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {

    private  ImageRepository imageRepository;
    private CommentRepository commentRepository;

    public void saveImage(ImageRequestDto imageRequestDto) {
        imageRepository.save(imageRequestDto.toEntity());
    }

    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    @Transactional
    public void createImageComment(CommentRequestDto commentRequestDto) {
        Image image = imageRepository.findById(commentRequestDto.getId()).orElseThrow(
                () -> new NullPointerException("댓글못단다고오오오")
        );

        Comment comment = new Comment(commentRequestDto, image);
        commentRepository.save(comment);
    }
}

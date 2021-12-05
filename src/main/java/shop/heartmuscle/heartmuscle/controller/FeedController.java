package shop.heartmuscle.heartmuscle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.Image;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import shop.heartmuscle.heartmuscle.dto.ImageRequestDto;
import shop.heartmuscle.heartmuscle.repository.FeedRepository;
import shop.heartmuscle.heartmuscle.service.FeedService;
import shop.heartmuscle.heartmuscle.service.ImageService;
import shop.heartmuscle.heartmuscle.service.S3Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;
    private final FeedRepository feedRepository;
    private final S3Service s3Service;
    private final ImageService imageService;

    // 피드 작성(text)
    @PostMapping("/api/feeds")
    public Feed createFeed(@RequestBody FeedRequestDto feedRequestDto) {
        return feedService.createFeed(feedRequestDto);
    }

    // 피드 작성 (image + text)
    @PostMapping("/v1/upload")
    public Feed uploadImage(@RequestPart(value="file",required = false) MultipartFile file, FeedRequestDto feedRequestDto) {
        return feedService.createFeed(feedRequestDto);
    }

    // 피드 작성 (image + text)
    @PostMapping("/feed/save")
    public void ImageFeed(ImageRequestDto imageRequestDto, MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        System.out.println("imgPath:::" + imgPath);
        imageRequestDto.setFilePath(imgPath);
        imageService.saveImage(imageRequestDto);
    }

    // 피드 목록(text) 불러오기
    @GetMapping("/api/feeds")
    public List<Feed> getFeeds() {
        return feedService.getFeeds();
    }

    // 피드 목록((image + text)) 불러오기
    @GetMapping("/api/feeds+image")
    public List<Image> getImages() {
        return imageService.getImages();
    }


    // 피드 상세보기
    @GetMapping("/api/feeds/{id}")
    public Feed getFeed(@PathVariable Long id) {
        return feedService.getFeed(id);
    }

    // 피드 수정하기
    @PutMapping("/api/feeds/{id}")
    public Long updateFeed(@PathVariable Long id, @RequestBody FeedRequestDto feedRequestDto) {
        feedService.update(id, feedRequestDto);
        return id;
    }

    // 피드 삭제하기
    @DeleteMapping("/api/feeds/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        feedRepository.deleteById(id);
        return id;
    }

    // 댓글작성
//    @PostMapping("/feeds/comment")
//    public void createComment(@RequestBody CommentRequestDto commentRequestDto) {
//        feedService.createComment(commentRequestDto);
//    }

    @PostMapping("/images/comment")
    public void createImageComment(@RequestBody CommentRequestDto commentRequestDto) {
        imageService.createImageComment(commentRequestDto);
    }

}


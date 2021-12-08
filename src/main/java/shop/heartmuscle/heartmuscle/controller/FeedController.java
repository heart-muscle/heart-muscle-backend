package shop.heartmuscle.heartmuscle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import shop.heartmuscle.heartmuscle.repository.FeedRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.FeedService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;
    private final FeedRepository feedRepository;
//    private final UserDetailsImpl userDetails;
//    private final S3Service s3Service;
//    private final ImageService imageService;

    // 피드 작성(text)
//    @PostMapping("/api/feeds")
//    public Feed createFeed(@RequestBody FeedRequestDto feedRequestDto) {
//        return feedService.createFeed(feedRequestDto);
//    }

    // 피드 작성 (image + text)
//    @PostMapping("/v1/upload")
//    public Feed uploadImage(@RequestPart(value="file",required = false) MultipartFile file, FeedRequestDto feedRequestDto) {
//        return feedService.createFeed(feedRequestDto);
//    }

    // 피드 작성 (image + text)
    @PostMapping("/feed")
    public void ImageFeed(FeedRequestDto feedRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
//        String imgPath = s3Service.upload(file);
//        System.out.println("imgPath:::" + imgPath);
//        imageRequestDto.setFilePath(imgPath);
//        imageService.saveImage(imageRequestDto);
        feedService.saveFeed(feedRequestDto, nowUser);
    }

    // 피드 목록(text) 불러오기
    @GetMapping("/feed")
    public List<Feed> getFeeds() {
        return feedService.getFeeds();
    }

    @GetMapping("/feed/check/{id}")
    public String checkUser(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return feedService.checkUser(id, nowUser);
    }

//    // 피드 목록((image + text)) 불러오기
//    @GetMapping("/api/feeds+image")
//    public List<Image> getImages() {
//        return imageService.getImages();
//    }


    // 피드 상세보기
    @GetMapping("/feed/{id}")
    public Feed getFeed(@PathVariable Long id) {
        return feedService.getFeed(id);
    }

    // 피드 수정하기
    @PutMapping("/feed/{id}")
    public Long updateFeed(@PathVariable Long id, @RequestBody FeedRequestDto feedRequestDto) {
        System.out.println("수정할 피드 아이디" + id);
        System.out.println("수정할 피드 내용" + feedRequestDto.getTitle());
        feedService.update(id, feedRequestDto);
        return id;
    }

    // 피드 삭제하기
    @DeleteMapping("/feed/{id}")
    public Long deleteFeed(@PathVariable Long id) {
        feedService.delete(id);
//        feedRepository.deleteById(id);
        return id;
    }

    // 댓글작성
    @PostMapping("/feed/comment")
    public void createComment(@RequestBody CommentRequestDto commentRequestDto) {
        System.out.println("댓글값확인" + commentRequestDto.getComment());
        System.out.println("피드아이디값확인"+ commentRequestDto.getId());
        feedService.createComment(commentRequestDto);
    }

//    @PostMapping("/images/comment")
//    public void createImageComment(@RequestBody CommentRequestDto commentRequestDto) {
//        imageService.createImageComment(commentRequestDto);
//    }

}
package shop.heartmuscle.heartmuscle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import shop.heartmuscle.heartmuscle.dto.UserDto;
import shop.heartmuscle.heartmuscle.repository.FeedRepository;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.FeedService;
import shop.heartmuscle.heartmuscle.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final UserService userService;
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
        System.out.println("유저네임여기같이들어오는지보자" + feedRequestDto.getUsername());
        System.out.println("피드이미지여기같이들어오는지보자" + feedRequestDto.getImage());
        feedService.saveFeed(feedRequestDto, nowUser);
    }

    // 피드 목록(text) 불러오기
    @GetMapping("/feed")
    public List<Feed> getFeeds() {
        return feedService.getFeeds();
    }

    // 전체 유저 불러오기
    @GetMapping("/user")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 유저 한명 불러오기
    @GetMapping("/user/{id}")
    public User getFeed(@PathVariable String id) {
        return userService.getUser(id);
    }

    // 유저 한명 불러오기
    @GetMapping("/user/detail/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    // 유저 프로필 수정하기
    @PostMapping("/user/detail")
    public String updateUser(UserDto userDto) throws IOException {
        System.out.println("수정할 피드 아이디" + userDto.getUsername());
        System.out.println("수정할 피드 이미지" + userDto.getImgUrl());
        System.out.println("수정할 피드 이미지" + userDto.getNickname());
        userService.update(userDto);
        return "완료!";
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
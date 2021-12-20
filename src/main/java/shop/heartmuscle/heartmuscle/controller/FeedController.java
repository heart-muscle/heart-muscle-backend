package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import shop.heartmuscle.heartmuscle.dto.FeedResponseDto;
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
    private final ModelMapper modelMapper;


    // 피드 작성 (image + text)
    @Operation(description = "피드 저장하기", method = "POST")
    @PostMapping("/feed")
    public void ImageFeed(FeedRequestDto feedRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        System.out.println("유저네임여기같이들어오는지보자" + feedRequestDto.getUsername());
        System.out.println("피드이미지여기같이들어오는지보자" + feedRequestDto.getImage());
        feedService.saveFeed(feedRequestDto, nowUser);
    }

    @Operation(description = "피드 목록 불러오기", method = "GET")
    // 피드 목록 dto 로 반환하기
    @GetMapping("/feed")
    public List<FeedResponseDto.Response> getFeedResponse() {
        List<Feed> feeds = feedService.getFeeds();
        List<FeedResponseDto.Response> response = modelMapper.map(feeds, new TypeToken<List<FeedResponseDto.Response>>() {}.getType());
        return response;
    }

    // 전체 유저 불러오기
    @Operation(description = "전체 유저 불러오기", method = "GET")
    @GetMapping("/user")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 유저 한명 불러오기
    @Operation(description = "특정 유저 정보 불러오기", method = "GET")
    @GetMapping("/user/{id}")
    public User getFeed(@PathVariable String id) {
        return userService.getUser(id);
    }

    // 유저 한명 불러오기
    @Operation(description = "특정 유저 정보 불러오기", method = "GET")
    @GetMapping("/user/detail/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    // 유저 프로필 수정하기
    @Operation(description = "유저 프로필 수정하기", method = "POST")
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
    @Operation(description = "피드 상세보기", method = "GET")
    @GetMapping("/feed/{id}")
    public Feed getFeed(@PathVariable Long id) {
        return feedService.getFeed(id);
    }

    // 피드 수정하기
    @Operation(description = "피드 수정하기", method = "PUT")
    @PutMapping("/feed/{id}")
    public Long updateFeed(@PathVariable Long id, @RequestBody FeedRequestDto feedRequestDto) {
        System.out.println("수정할 피드 아이디" + id);
        System.out.println("수정할 피드 내용" + feedRequestDto.getTitle());
        feedService.update(id, feedRequestDto);
        return id;
    }

    // 피드 삭제하기
    @Operation(description = "피드 삭제하기", method = "DELETE")
    @DeleteMapping("/feed/{id}")
    public Long deleteFeed(@PathVariable Long id) {
        feedService.delete(id);
//        feedRepository.deleteById(id);
        return id;
    }

    // 댓글작성
    @Operation(description = "피드에 댓글 작성하기", method = "POST")
    @PostMapping("/feed/comment")
    public void createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        feedService.createComment(commentRequestDto, nowUser);
    }

//    @PostMapping("/images/comment")
//    public void createImageComment(@RequestBody CommentRequestDto commentRequestDto) {
//        imageService.createImageComment(commentRequestDto);
//    }

}
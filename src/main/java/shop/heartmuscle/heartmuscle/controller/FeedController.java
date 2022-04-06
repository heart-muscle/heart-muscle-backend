package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.heartmuscle.heartmuscle.entity.Feed;
import shop.heartmuscle.heartmuscle.entity.User;
import shop.heartmuscle.heartmuscle.dto.*;
import shop.heartmuscle.heartmuscle.dto.request.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.request.FeedRequestDto;
import shop.heartmuscle.heartmuscle.dto.response.UserResponseDto;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.FeedServiceImpl;
import shop.heartmuscle.heartmuscle.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedServiceImpl feedService;
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

    @GetMapping("/feed")
    public Page<Feed> getFeedResponses(@RequestParam("page") int page,
                                       @RequestParam("size") int size,
                                       @RequestParam("sortBy") String sortBy,
                                       @RequestParam("isAsc") boolean isAsc) {
        page = page - 1;
        return feedService.getFeeds(page, size, sortBy, isAsc);
    }

    // 현재 로그인 사용자 - 피드 작성자 일치 여부 확인
    @GetMapping("/feed/check/{id}")
    public String checkUser(@PathVariable Long id,
                            @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return feedService.checkUser(id, nowUser);
    }

    // 피드 상세보기 - dto [ version 1 / 2 ]
        @Operation(description = "피드 상세보기", method = "GET")
        @GetMapping("/feed/{id}")
        public Feed getFeed(@PathVariable Long id) {
            return feedService.getFeed(id);
        }

    // 피드 수정하기
    @Operation(description = "피드 수정하기", method = "PUT")
    @PutMapping("/feed/{id}")
    public Long updateFeed(@PathVariable Long id, @RequestBody FeedRequestDto feedRequestDto) {
        feedService.update(id, feedRequestDto);
        return id;
    }

    // 피드 삭제하기
    @Operation(description = "피드 삭제하기", method = "DELETE")
    @DeleteMapping("/feed/{id}")
    public Long deleteFeed(@PathVariable Long id) {
        feedService.delete(id);
        return id;
    }

    // 댓글작성
    @Operation(description = "피드에 댓글 작성하기", method = "POST")
    @PostMapping("/feed/comment")
    public void createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        feedService.createComment(commentRequestDto, nowUser);
    }

}
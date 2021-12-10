package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
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


    // 피드 작성 (image + text)
    @Operation(description = "피드 저장하기", method = "POST")
    @PostMapping("/feed")
    public void ImageFeed(FeedRequestDto feedRequestDto, @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        System.out.println("유저네임여기같이들어오는지보자" + feedRequestDto.getUsername());
        System.out.println("피드이미지여기같이들어오는지보자" + feedRequestDto.getImage());
        feedService.saveFeed(feedRequestDto, nowUser);
    }

    // 피드 목록(text) 불러오기
    @Operation(description = "피드 목록 불러오기", method = "GET")
    @GetMapping("/feed")
    public List<Feed> getFeeds() {
        return feedService.getFeeds();
    }

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
    public void createComment(@RequestBody CommentRequestDto commentRequestDto) {
        System.out.println("댓글값확인" + commentRequestDto.getComment());
        System.out.println("피드아이디값확인"+ commentRequestDto.getId());
        feedService.createComment(commentRequestDto);
    }
}
package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.*;
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

    // 피드 목록 dto 로 반환하기 [ version 1 / 4 ]
    //    @GetMapping("/feed")
    //    public List<FeedResponseDto.Response> getFeedResponse() {
    //        List<Feed> feeds = feedService.getFeeds();
    //        List<FeedResponseDto.Response> response = modelMapper.map(feeds, new TypeToken<List<FeedResponseDto.Response>>() {
    //        }.getType());
    //        return response;
    //    }

    // 피드 목록 반환하기 [ version 2 / 4 ]
    //    @GetMapping("/feed")
    //    public List<Feed> getFeedResponse1() {
    //        List<Feed> feeds = feedService.getFeeds();
    //        return feeds;
    //    }

    // 피드 목록 dto 로 반환하기 + 페이징 [ version 3 / 4 ]
    //    @GetMapping("/feed")
    //    public Page<FeedResponseDto.Response> getFeedResponse(@RequestParam("page") int page,
    //                                                          @RequestParam("size") int size,
    //                                                          @RequestParam("sortBy") String sortBy,
    //                                                          @RequestParam("isAsc") boolean isAsc) {
    //        page = page - 1;
    //        Page<Feed> feeds = feedService.getFeeds(page , size, sortBy, isAsc);
    //
    //        Page<FeedResponseDto.Response> response = modelMapper.map(feeds, new TypeToken<Page<FeedResponseDto.Response>>() {}.getType());
    //        return response;
    //    }

    // 피드 목록 반환하기 + 페이징 [ version 4 / 4 ]
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

    // 피드 상세보기 + dto [ version 2 ]
    //    @GetMapping("/feed/{id}")
    //    public FeedResponseDto.Response getFeed(@PathVariable Long id) {
    //        Feed feed = feedService.getFeed(id);
    //        FeedResponseDto.Response response = modelMapper.map(feed, new TypeToken<FeedResponseDto.Response>() {
    //        }.getType());
    //        return response;
    //    }

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

    // ----------------------------------마이페이지---------------------------------- //
    // 전체 유저 불러오기
    @Operation(description = "전체 유저 불러오기", method = "GET")
    @GetMapping("/user")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 유저 정보 불러오기 [ version 1 ]
    //    @Operation(description = "특정 유저 정보 불러오기", method = "GET")
    //    @GetMapping("/user/{id}")
    //    public User getUser(@PathVariable String id) {
    //        return userService.getUser(id);
    //    }

    // 유저 정보 불러오기( id = user_id )
    //    @GetMapping("/user/{id}")
    //    public UserResponseDto.Response getUserInfo(@PathVariable Long id) {
    //        User user = userService.getUserByID(id);
    //        UserResponseDto.Response response = modelMapper.map(user, new TypeToken<UserResponseDto.Response>() {}.getType());
    //        return response;
    //    }

    // 유저 정보 불러오기( id = username )
    @Operation(description = "특정 유저 정보 불러오기", method = "GET")
    @GetMapping("/user/{id}")
    public UserResponseDto.Response getUser(@PathVariable String id) {
        User user = userService.getUserByUsername(id);
        UserResponseDto.Response response = modelMapper.map(user, new TypeToken<UserResponseDto.Response>() {}.getType());
        return response;
    }

    // 유저 프로필 수정하기
    @Operation(description = "유저 프로필 수정하기", method = "POST")
    @PostMapping("/user/detail")
    public String updateUser(UserDto userDto) throws IOException {
        userService.update(userDto);
        return "완료!";
    }
}
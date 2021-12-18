package shop.heartmuscle.heartmuscle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.domain.Comment;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.domain.WorkoutTag;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import shop.heartmuscle.heartmuscle.dto.UserDto;
import shop.heartmuscle.heartmuscle.repository.CommentRepository;
import shop.heartmuscle.heartmuscle.repository.FeedRepository;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.repository.WorkoutTagRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final AwsService awsService;
    private final WorkoutTagRepository workoutTagRepository;
//    private final UserDetailsImpl nowUser;
    private final UserRepository userRepository;

//    public Feed createFeed(FeedRequestDto feedRequestDto) {
//        Feed feed = new Feed(feedRequestDto);
//        feedRepository.save(feed);
//        return feed;
//    }

//    public FeedFile createFeedFile(FeedRequestDto feedRequestDto) {
//        Feed feed = new Feed(feedRequestDto);
//        feedRepository.save(feed);
//        return feed;
//    }

    public List<Feed> getFeeds() {
        return feedRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

//    public List<Feed> getFeed(UserDetailsImpl nowUser) {
//
//
//        User user = userRepository.findById(nowUser.getId()).orElseThrow(
//                () -> new NullPointerException("해당 User 없음")
//        );
//
////        nowUser.getId() = 1
////                user_id = 1
//
//        feedRepository.
//
//        Optional<User> findByUsername(String username);
//
//        feedRepository.findByUserID
//    }



    // 테스트중
//    public Feed getFeed(Long id, UserDetailsImpl nowUser) {
//
//        User user = userRepository.findById(nowUser.getId()).orElseThrow(
//                () -> new NullPointerException("해당 User 없음")
//        );
//
//        if (user.getId().equals(nowUser.getId())) {
//            String writerCheck = "true";
//        }
//
//        return feedRepository.findById(id).orElseThrow(
//                () -> new NullPointerException("존재하지않습니다.")
//        );
//
//        //
//    }

    public Feed getFeed(Long id) {

        return feedRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지않습니다.")
        );
    }

    public String checkUser(Long id, UserDetailsImpl nowUser) {

        String check = null;

        // 1
        Long nowuser = nowUser.getId(); // 1
        System.out.println("nowuser찾기" + nowuser);

        Feed checkfeed = feedRepository.findById(id).orElseThrow(
                () -> new NullPointerException("작성하신 피드가 아닙니다")
        );

        User findfeeduser = checkfeed.getUser();
        Long feeduser = findfeeduser.getId();
        System.out.println("feeduser찾기" + feeduser);

        if (nowuser == feeduser) check = "true";
        else check = "false";

        return check;
    }

    public void delete(Long id) {
        // 피드에 달려있는
        feedRepository.deleteById(id);
    }

    @Transactional
    public Long update(Long id, FeedRequestDto feedRequestDto) {
        Feed feed = feedRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );
        feed.update(feedRequestDto);
        return feed.getId();
    }

    @Transactional
    public void createComment(CommentRequestDto commentRequestDto) {
        System.out.println("댓글 저장할 피드 찾아오기" + feedRepository.findById(commentRequestDto.getId()));
        Feed feed = feedRepository.findById(commentRequestDto.getId()).orElseThrow(
                () -> new NullPointerException("댓글못단다고오오오")
        );

        Comment comment = new Comment(commentRequestDto, feed);
        commentRepository.save(comment);
    }

    @Transactional
    public Feed saveFeed(FeedRequestDto feedRequestDto, UserDetailsImpl nowUser) throws IOException {
        String url = null;

        if(feedRequestDto.getImage() != null) url = awsService.upload(feedRequestDto.getImage());
        else url = "https://teamco-spring-project.s3.ap-northeast-2.amazonaws.com/logo.png";

//        System.out.println("user id 찾기" + nowUser.getUsername());
        System.out.println("user id 찾기" + nowUser.getId());

        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );

        Feed feed = new Feed(feedRequestDto, url, user);
        feedRepository.save(feed);

        List<String> items = Arrays.asList(feedRequestDto.getTags().split("\\s*,\\s*"));
        List<WorkoutTag> tags = items.stream().map(tag -> new WorkoutTag(tag, feed)).collect(Collectors.toList());
        workoutTagRepository.saveAll(tags);

        return feed;
    }

}

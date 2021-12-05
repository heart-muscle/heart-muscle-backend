package shop.heartmuscle.heartmuscle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.domain.Comment;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.Workout;
import shop.heartmuscle.heartmuscle.domain.WorkoutTag;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import shop.heartmuscle.heartmuscle.repository.CommentRepository;
import shop.heartmuscle.heartmuscle.repository.FeedRepository;
import shop.heartmuscle.heartmuscle.repository.WorkoutTagRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;
    private final AwsService awsService;
    private final WorkoutTagRepository workoutTagRepository;

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
        return feedRepository.findAll();
    }

    public Feed getFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지않습니다.")
        );
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
        Feed feed = feedRepository.findById(commentRequestDto.getId()).orElseThrow(
                () -> new NullPointerException("댓글못단다고오오오")
        );

        Comment comment = new Comment(commentRequestDto, feed);
        commentRepository.save(comment);
    }

    @Transactional
    public Feed saveFeed(FeedRequestDto feedRequestDto) throws IOException {
        String url = null;
        System.out.println("url:::" + url);
        if(feedRequestDto.getImage() != null) url = awsService.upload(feedRequestDto.getImage());
        System.out.println("url:::::" + url);
        Feed feed = new Feed(feedRequestDto, url);
        feedRepository.save(feed);

        List<String> items = Arrays.asList(feedRequestDto.getTags().split("\\s*,\\s*"));
        List<WorkoutTag> tags = items.stream().map(tag -> new WorkoutTag(tag, feed)).collect(Collectors.toList());
        workoutTagRepository.saveAll(tags);

        return feed;
    }

}


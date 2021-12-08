package shop.heartmuscle.heartmuscle.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Feed extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String imageUrl;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy="feed", cascade = CascadeType.ALL)
    private Set<WorkoutTag> tags;

    @JsonIgnore // 이게 무한루프에 빠지는 걸 방지해줌
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Feed(FeedRequestDto feedRequestDto) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
    }

    public Feed(FeedRequestDto feedRequestDto, String imageUrl) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
        this.imageUrl = imageUrl;
    }

    public Feed(FeedRequestDto feedRequestDto, String imageUrl, User user) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
        this.imageUrl = imageUrl;
        this.user = user;
    }

//    public Feed(FeedRequestDto feedRequestDto, String imageUrl, UserDetailsImpl nowUser) {
//        this.title = feedRequestDto.getTitle();
//        this.content = feedRequestDto.getContent();
//        this.imageUrl = imageUrl;
//        this.user = nowUser.getUser();
//    }

    public void update(FeedRequestDto feedRequestDto) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
    }
}
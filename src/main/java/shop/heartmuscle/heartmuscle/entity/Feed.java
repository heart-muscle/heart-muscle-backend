package shop.heartmuscle.heartmuscle.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.heartmuscle.heartmuscle.dto.request.FeedRequestDto;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Feed(FeedRequestDto feedRequestDto, String imageUrl, User user) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public void update(FeedRequestDto feedRequestDto) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
    }
}
package shop.heartmuscle.heartmuscle.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;

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

    @OneToMany(mappedBy = "feed")
    private List<Comment> comments;

    @OneToMany(mappedBy="feed")
    private Set<WorkoutTag> tags;

    public Feed(FeedRequestDto feedRequestDto) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
    }

    public Feed(FeedRequestDto feedRequestDto, String imageUrl) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
        this.imageUrl = imageUrl;
    }

    public void update(FeedRequestDto feedRequestDto) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
    }
}
package shop.heartmuscle.heartmuscle.domain;

import lombok.Setter;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
//@Setter
@Entity
public class Feed extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

//    @OneToMany(mappedBy = "feed")
//    private List<Comment> comments;

    public Feed(FeedRequestDto feedRequestDto) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
    }

    public Feed(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(FeedRequestDto feedRequestDto) {
        this.title = feedRequestDto.getTitle();
        this.content = feedRequestDto.getContent();
    }
}

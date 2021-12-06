package shop.heartmuscle.heartmuscle.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.heartmuscle.heartmuscle.dto.CommentRequestDto;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

//    @JsonIgnore // 이게 무한루프에 빠지는 걸 방지해줌
//    @ManyToOne
//    @JoinColumn(name = "feed_id", nullable = false)
//    private Feed feed;

    @JsonIgnore // 이게 무한루프에 빠지는 걸 방지해줌
    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

//    public Comment(CommentRequestDto commentRequestDto, Feed feed) {
//        this.comment = commentRequestDto.getComment();
//        this.feed = feed;
//    }

    public Comment(CommentRequestDto commentRequestDto, Feed feed) {
        this.comment = commentRequestDto.getComment();
    }

}

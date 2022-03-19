package shop.heartmuscle.heartmuscle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.heartmuscle.heartmuscle.dto.request.CommentRequestDto;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String commentUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    public Comment(CommentRequestDto commentRequestDto, Feed feed, String commentUser) {
        this.comment = commentRequestDto.getComment();
        this.feed = feed;
        this.commentUser = commentUser;
    }

}
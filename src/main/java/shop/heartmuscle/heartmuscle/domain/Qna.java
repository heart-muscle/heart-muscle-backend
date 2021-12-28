package shop.heartmuscle.heartmuscle.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import shop.heartmuscle.heartmuscle.dto.QnaRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Qna extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "qna", cascade = CascadeType.REMOVE)
    private List<QnaComment> comment;


    public Qna(QnaRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
//        this.username = requestDto.getUsername();
    }

    public void update(QnaRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}

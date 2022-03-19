package shop.heartmuscle.heartmuscle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.heartmuscle.heartmuscle.dto.UserDto;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User extends Timestamped {

    public User(String username, String password, String email, UserRole role, String nickname, String imgUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = null;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
    }

    public User(String username, String password, String email, UserRole role, Long kakaoId, String imgUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
        this.imgUrl = imgUrl;
        this.nickname = username;
    }

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long id;

    // 반드시 값을 가지도록 합니다.
//    @Column(name = "username", nullable = false)
//    private String username;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(nullable = true)
    private Long kakaoId;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Feed> feeds;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Qna> qna;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<QnaComment> qnaComment;

    public void update(UserDto userDto, String imgUrl) {
        this.imgUrl = imgUrl;
        this.nickname = userDto.getNickname();
    }
}
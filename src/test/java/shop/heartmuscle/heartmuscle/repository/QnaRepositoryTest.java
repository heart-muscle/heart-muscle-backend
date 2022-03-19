package shop.heartmuscle.heartmuscle.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.entity.Qna;
import shop.heartmuscle.heartmuscle.entity.User;
import shop.heartmuscle.heartmuscle.entity.UserRole;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class QnaRepositoryTest {

    @Autowired
    QnaRepository qnaRepository;

    @Autowired
    UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setup() {
        user1 = new User("test1", "12345678", "test@email.com", UserRole.USER, "123456", "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png");
        user2 = new User("test2", "87654321", "test@email.com", UserRole.USER, "123456", "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png");

        userRepository.save(user1);
        userRepository.save(user2);

    }

    @Test
    @Order(1)
    public void saveDelete() {
        // given

        // dto 방식
        QnaRequestDto requestDto = new QnaRequestDto("제목", "내용");
        Qna qna = new Qna(requestDto, user1);

        // entity 방식
        Qna qna1 = new Qna(null, "제목", "내용", user1, null);

        // when
        qnaRepository.save(qna);
        qnaRepository.save(qna1);

        qnaRepository.delete(qna);
        qnaRepository.delete(qna1);
    }

    @Test
    @Order(2)
    public void findAll() {
        // given
        Qna qna1 = new Qna(null, "제목1", "내용1", user1, null);
        Qna qna2 = new Qna(null, "제목2", "내용2", user2, null);
        Qna qna3 = new Qna(null, "제목3", "내용3", user2, null);

        qnaRepository.save(qna1);
        qnaRepository.save(qna2);
        qnaRepository.save(qna3);

        // when


        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Qna> all = qnaRepository.findAll(pageable);

        // then
        assertThat(all.getNumberOfElements()).isEqualTo(3);
        assertThat(all.getSort()).isEqualTo(Sort.by("id").descending());
        assertThat(all.getSize()).isEqualTo(10);
    }

}
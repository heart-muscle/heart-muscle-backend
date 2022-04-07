package shop.heartmuscle.heartmuscle.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.entity.Qna;
import shop.heartmuscle.heartmuscle.entity.User;
import shop.heartmuscle.heartmuscle.entity.UserRole;
import shop.heartmuscle.heartmuscle.exception.ApiRequestException;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.service.UserService;
import shop.heartmuscle.heartmuscle.service.qna.QnaService;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QnaIntegrationTest {

    @Autowired
    QnaService qnaService;
    @Autowired
    UserService userService;
    @Autowired
    QnaRepository qnaRepository;
    @Autowired
    UserRepository userRepository;

    User testUser;
    UserDetailsImpl nowUser;
    QnaRequestDto qnaRequestDto;
    Qna qnaEntity;

    @BeforeEach
    void init() {

        this.testUser = new User("test", "12345678", "test@test.com", UserRole.USER, "12345", "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png");
        this.userRepository.save(testUser);
        this.qnaRequestDto = new QnaRequestDto("제목 1", "내용 1");

        this.qnaEntity = QnaRequestDto.toQna(qnaRequestDto);
        qnaEntity.setUser(testUser);


        this.nowUser = new UserDetailsImpl(testUser);

    }


    @Test
    @Order(1)
    @Transactional
    @DisplayName("게시물 생성")
    void createQna() throws IOException {
        // given
        Qna qna = qnaService.createQna(qnaEntity);

        // when
        Qna qnaTest = qnaRepository.findById(qna.getId()).orElseThrow(
                () -> new ApiRequestException("게시물이 정상적으로 생성되지 않았습니다.")
        );

        // then
        Assertions.assertEquals(qna.getId(), qnaTest.getId());
    }

    @Test
    @Order(2)
    @Transactional
    @DisplayName("게시물 삭제")
    void deleteQna() throws IOException {
        // given
        Qna qna = qnaService.createQna(qnaEntity);


        // when
        qnaService.delete(qna.getId(), nowUser);

        // then
        Optional<Qna> qnaTest = qnaRepository.findById(qna.getId());
        if (qnaTest.isPresent())
            throw new IllegalArgumentException("UserReview 가 정상적으로 삭제되지 않았습니다.");
        else
            assertEquals("Comment 가 정상적으로 삭제되었습니다.", Optional.empty(), qnaTest);

    }


}

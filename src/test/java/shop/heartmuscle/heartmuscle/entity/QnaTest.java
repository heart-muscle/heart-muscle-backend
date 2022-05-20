package shop.heartmuscle.heartmuscle.entity;

import org.junit.jupiter.api.*;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import shop.heartmuscle.heartmuscle.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


class QnaTest {

    @Nested
    @DisplayName("게시글 객체 생성")
    class CreateQna {

        private String title;
        private String content;
        private QnaRequestDto qnaRequestDto;
        private User user;
        private UserRepository userRepository;
        private QnaRepository qnaRepository;

        @BeforeEach
        void setup() {
            qnaRequestDto = new QnaRequestDto("제목 1", "내용 1");
            user = new User("test", "12345678", "test@test.com", UserRole.USER, "testnickname", "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png");
        }

        @AfterAll
        void clear() {
            userRepository.deleteAll();
            qnaRepository.deleteAll();
        }

        @Test
        @DisplayName("정상 케이스")
        void createQna() {
            // given
            Qna qnaEntity = QnaRequestDto.toQna(qnaRequestDto);
            qnaEntity.setUser(user);

            // when
            Qna qna = new Qna(qnaRequestDto, user);

            // then
            assertEquals(qna.getId(), null);
            assertEquals(qna.getTitle(), title);
            assertEquals(qna.getContent(), content);
        }
    }
}
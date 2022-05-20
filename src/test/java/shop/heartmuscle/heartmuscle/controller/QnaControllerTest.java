package shop.heartmuscle.heartmuscle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.entity.Qna;
import shop.heartmuscle.heartmuscle.entity.User;
import shop.heartmuscle.heartmuscle.entity.UserRole;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import shop.heartmuscle.heartmuscle.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QnaControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QnaRepository qnaRepository;
    @Autowired
    ModelMapper modelMapper;

    private User testUser;

    @BeforeAll
    void init() {

        testUser = new User("test", "12345678", "test@test.com", UserRole.USER, "12345", "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png");
        this.userRepository.save(testUser);

    }

    @AfterAll
    void clear() {
        userRepository.deleteAll();
        qnaRepository.deleteAll();
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }


    @Test
    @DisplayName("게시글 생성")
    @Order(1)
    @WithUserDetails(value = "test")
    void createQna() throws Exception {
        QnaRequestDto qnaRequestDto = new QnaRequestDto("제목2", "내용2");

        Qna map = modelMapper.map(qnaRequestDto, Qna.class);

        assertThat(map.getContent()).isEqualTo(qnaRequestDto.getContent());

        Qna qna2 = new Qna(qnaRequestDto, testUser);

        String jsonString = objectMapper.writeValueAsString(qna2);
        mockMvc.perform(post("/qna")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                        .andExpectAll(
                                status().isCreated(),
                                jsonPath("$.id").value(1L)
                        )
                        .andDo(print());

        Optional<Qna> findQna = qnaRepository.findById(1L);

        assertThat(findQna.get().getTitle()).isEqualTo("제목2");
        assertThat(findQna.get().getContent()).isEqualTo("내용2");
    }

    @Test
    @DisplayName("게시글 조회")
    @Order(2)
    @WithUserDetails(value = "test")
    void findQnaById() throws Exception {

        Qna qna3 = new Qna(null, "제목3", "내용3", testUser, null);
        qnaRepository.save(qna3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/qna/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpectAll(
                                status().isOk(),
                                MockMvcResultMatchers.jsonPath("$.id").value(2));


    }

    @Test
    @DisplayName("modelmapper 확인")
    @Order(3)
    @WithUserDetails(value = "test")
    void model() throws Exception {
        QnaRequestDto qnaRequestDto = new QnaRequestDto("제목2", "내용2");

        Qna map = modelMapper.map(qnaRequestDto, Qna.class);

        assertThat(map.getContent()).isEqualTo(qnaRequestDto.getContent());
        assertThat(map.getTitle()).isEqualTo(qnaRequestDto.getTitle());
    }

}
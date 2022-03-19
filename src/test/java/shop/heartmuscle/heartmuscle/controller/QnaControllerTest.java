package shop.heartmuscle.heartmuscle.controller;

import com.google.gson.GsonBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private Long qnaId;
    private User testUser;

    @BeforeAll
    void init() {
        User testUser = new User("test", "12345678", "test@test.com", UserRole.USER, "12345", "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png");
        this.userRepository.save(testUser);

//        QnaRequestDto qnaRequestDtoTest = new QnaRequestDto("제목 1", "내용 1");
//        Qna qnaEntity = QnaRequestDto.toQna(qnaRequestDtoTest);
        Qna qnaTest = new Qna(1L, "제목", "내용", testUser, null);
//        qnaEntity.setUser(userTest);
        this.qnaRepository.save(qnaTest);

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
        QnaRequestDto qnaRequestDto = new QnaRequestDto("제목", "내용");

        Qna qna = new Qna(qnaRequestDto, testUser);
        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(qna);


        mockMvc.perform(MockMvcRequestBuilders.post("/qna")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                        .andExpect(status().isCreated())
                        .andDo(print());
    }
}
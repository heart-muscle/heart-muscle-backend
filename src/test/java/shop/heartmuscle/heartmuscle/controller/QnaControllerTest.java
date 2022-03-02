package shop.heartmuscle.heartmuscle.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class QnaControllerTest {

    @Test
    void createQna() {
    }

//    @Test
//    void getQna() {
//    }
//
//    @Test
//    void testGetQna() {
//    }
//
//    @Test
//    void updateQna() {
//    }
//
//    @Test
//    void deleteQna() {
//    }
}
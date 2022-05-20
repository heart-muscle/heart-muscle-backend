//package shop.heartmuscle.heartmuscle.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//import shop.heartmuscle.heartmuscle.entity.Qna;
//import shop.heartmuscle.heartmuscle.entity.User;
//import shop.heartmuscle.heartmuscle.repository.QnaRepository;
//import shop.heartmuscle.heartmuscle.repository.UserRepository;
//import shop.heartmuscle.heartmuscle.service.qna.QnaService;
//
//@SpringBootTest
//@Transactional
//@TestPropertySource(locations="classpath:application-test.properties")
//class QnaServiceTest {
//
//    @Autowired
//    private QnaService qnaService;
//
//    @Autowired
//    private QnaRepository qnaRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @DisplayName("Qna 작성")
//    void createQna() {
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("JD");
//        user.setNickname("Passion");
//        user.setPassword("12345678");
//        userRepository.save(user);
//
//
//        Qna qna = new Qna();
//        qna.setUser(user);
//        qna.setContent("test");
//        qna.setTitle("test");
//        qnaRepository.save(qna);
//    }
//
//    @Test
//    @DisplayName("qna 한개 조회")
//    void qnaOne() {
//        Long id = 1L;
//
//        Qna qna = qnaService.getQnaOne(1L);
//
//        System.out.println(qna);
//    }
//
//}
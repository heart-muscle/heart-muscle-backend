package shop.heartmuscle.heartmuscle.exception;

public class QnaNotFoundException extends RuntimeException {

    public QnaNotFoundException(Long id) {
        super(id + "번 게시물을 찾을 수 없습니다.");
    }

    public QnaNotFoundException(String string) {
        super(string);
    }

}

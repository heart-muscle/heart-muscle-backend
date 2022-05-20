package shop.heartmuscle.heartmuscle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class QnaNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(QnaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(QnaNotFoundException ex) {
        return ex.getMessage();
    }
}

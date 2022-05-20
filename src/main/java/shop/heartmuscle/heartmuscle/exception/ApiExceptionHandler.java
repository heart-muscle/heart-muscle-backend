package shop.heartmuscle.heartmuscle.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException ex) {
        ApiException apiException = new ApiException(
                ex.getMessage(),
                // HTTP 404 -> Client Error
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(
                apiException,
                // HTTP 404 -> Client Error
                HttpStatus.NOT_FOUND
        );
    }
}


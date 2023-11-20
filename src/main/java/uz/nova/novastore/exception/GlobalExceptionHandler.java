package uz.nova.novastore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.nova.novastore.domain.StandardResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<StandardResponse<Object>> dataNotFoundExceptionHandler(
            DataNotFoundException e) {
        return ResponseEntity.status(404).body(StandardResponse.builder().status(404).message(e.getMessage()).data(null).build());
    }

    @ExceptionHandler(value = {NotAcceptableException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<StandardResponse<Object>> notAcceptableException(NotAcceptableException e) {
        return ResponseEntity.status(409).body(StandardResponse.builder().status(409).message(e.getMessage()).data(null).build());
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<StandardResponse<?>> forbiddenException(ForbiddenException e) {
        return ResponseEntity.status(403).body(StandardResponse.builder().status(409).message(e.getMessage()).data(null).build());
    }
}

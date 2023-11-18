package uz.nova.novastore.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.nova.novastore.domain.response.StandardResponse;
import uz.nova.novastore.domain.response.Status;
import uz.nova.novastore.exception.DataNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<StandardResponse<String>> dataNotFoundExceptionHandler(
            DataNotFoundException e){
        return ResponseEntity.status(404).body(StandardResponse.<String>builder().status(Status.ERROR).message(e.getMessage()).build());
    }
}

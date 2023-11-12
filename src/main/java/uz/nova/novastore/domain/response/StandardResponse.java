package uz.nova.novastore.domain.response;

import lombok.*;

@Data
@Builder
public class StandardResponse <T> {
    private Status status;
    private String message;
    private T data;


}

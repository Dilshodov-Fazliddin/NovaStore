package uz.nova.novastore.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message){
        super(message);
    }
}

package uz.nova.novastore.service;

import org.springframework.http.ResponseEntity;
import uz.nova.novastore.domain.*;

import java.util.UUID;

public interface UserService {
    ResponseEntity<StandardResponse<?>> signUp(UserCreateDto userCreate);


    ResponseEntity<StandardResponse<?>> verify(Integer code, String email);

    ResponseEntity<StandardResponse<JwtResponse>> login(LoginDto loginDto);

    ResponseEntity<StandardResponse<?>> forgetPassword(String email);

    ResponseEntity<StandardResponse<?>> verifyForgetPassword(VerifyForgetPasswordDto verifyForgetPasswordDto);
}
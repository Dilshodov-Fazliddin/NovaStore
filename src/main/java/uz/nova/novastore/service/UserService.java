package uz.nova.novastore.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.LoginDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.domain.UserCreateDto;
import uz.nova.novastore.domain.VerifyForgetPasswordDto;

@Service
public interface
UserService {

    ResponseEntity<StandardResponse<?>> signUp(UserCreateDto userCreate);

    ResponseEntity<StandardResponse<?>> verify(Integer code, String email);

    ResponseEntity<StandardResponse<Object>> login(LoginDto loginDto);

    ResponseEntity<StandardResponse<?>> forgetPassword(String email);

    ResponseEntity<StandardResponse<?>> verifyForgetPassword(VerifyForgetPasswordDto verifyForgetPasswordDto);
}
package uz.nova.novastore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.*;
import uz.nova.novastore.entity.UserEntity;

import java.util.List;

@Service
public interface UserService {

    ResponseEntity<StandardResponse<?>> signUp(UserCreateDto userCreate);

    ResponseEntity<StandardResponse<?>> verify(Integer code, String email);

    ResponseEntity<StandardResponse<Object>> login(LoginDto loginDto);

    ResponseEntity<StandardResponse<?>> forgetPassword(String email);

    ResponseEntity<StandardResponse<?>> verifyForgetPassword(VerifyForgetPasswordDto verifyForgetPasswordDto);
    ResponseEntity<StandardResponse<Object>>getAllUsers(int page,int size);
   Page<UserForFront> mapUsers(List<UserEntity>userEntities, Pageable pageable);
}
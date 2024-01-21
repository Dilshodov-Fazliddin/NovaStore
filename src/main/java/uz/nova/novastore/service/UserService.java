package uz.nova.novastore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.*;
import uz.nova.novastore.domain.request.ProfileDto;
import uz.nova.novastore.entity.UserEntity;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    ResponseEntity<StandardResponse<?>> signUp(UserCreateDto userCreate);

    ResponseEntity<StandardResponse<?>> verify(Integer code, String email);

    ResponseEntity<StandardResponse<Object>> login(LoginDto loginDto);

    ResponseEntity<StandardResponse<?>> forgetPassword(String email);

    ResponseEntity<StandardResponse<?>> verifyForgetCode(VerifyForgetPasswordDto passwordDto);
    ResponseEntity<StandardResponse<?>> verifyForgetSetNewPassword(String email,String newPassword);
   Page<ProfileDto> mapUsers(List<UserEntity>userEntities, Pageable pageable);
   ResponseEntity<StandardResponse<?>>blockUsers(UUID id);
   ResponseEntity<StandardResponse<?>>unblockUsers(UUID id);
   ResponseEntity<StandardResponse<Object>> getAllUsers(int page,int size);
   ResponseEntity<StandardResponse<List<ProfileDto>>>getCustomers(int page,int size);
   ResponseEntity<StandardResponse<Integer>>getNumberOfUsers();
}
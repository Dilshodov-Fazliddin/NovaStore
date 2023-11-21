package uz.nova.novastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.*;
import uz.nova.novastore.entity.UserEntity;
import uz.nova.novastore.exception.DataNotFoundException;
import uz.nova.novastore.exception.ForbiddenException;
import uz.nova.novastore.exception.NotAcceptableException;
import uz.nova.novastore.jwt.JwtService;
import uz.nova.novastore.jwt.JwtTokenFilter;
import uz.nova.novastore.mapper.UserMapper;
import uz.nova.novastore.repository.UserRepository;
import uz.nova.novastore.service.UserService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public ResponseEntity<StandardResponse<?>> signUp(UserCreateDto userCreate) {
        if (userRepository.existsByEmail(userCreate.getEmail()))
            throw new NotAcceptableException("This email already used");
        int randomCode = new Random().nextInt(9000) + 1000;
        mailService.sendVerifyCode(randomCode, userCreate.getEmail());
        userRepository.save(userMapper.toEntity(userCreate, randomCode));
        return ResponseEntity.ok(StandardResponse.builder().message("We sent emailCode for confirmation").status(200).data(null).build());
    }

    @Override
    public ResponseEntity<StandardResponse<?>> verify(Integer code, String email) {
        UserEntity user = userRepository.findByEmailAndEmailCode(email, code).orElseThrow(() -> new ForbiddenException("Email code is not present"));
        user.setEmailCode(null);
        user.setIsEnabled(true);
        return ResponseEntity.ok(StandardResponse.builder().status(200).message("User verified").data(jwtService.generateAccessToken(userRepository.save(user))).build());
    }

    @Override
    public ResponseEntity<StandardResponse<Object>> login(LoginDto loginDto) {
        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new DataNotFoundException("User not found"));
        if (passwordEncoder.matches(user.getPassword(), loginDto.getPassword())) {
            return ResponseEntity.ok(StandardResponse.builder().status(200).message("User successfully login in system").
                    data(JwtResponse.builder().accessToken(jwtService.generateAccessToken(user)).build())
                    .build());
        }
        throw new NotAcceptableException("Password or username isn't correct");
    }

    @Override
    public ResponseEntity<StandardResponse<?>> forgetPassword(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found"));
        int code = new Random().nextInt(1000, 9999);
        mailService.sendVerifyCode(code, email);
        return ResponseEntity.ok(StandardResponse.builder().status(200).message("send verify code").data(userRepository.save(user)).build());
    }

    @Override
    public ResponseEntity<StandardResponse<?>> verifyForgetPassword(VerifyForgetPasswordDto verifyForgetPasswordDto) {
        UserEntity user = userRepository.findByEmailAndEmailCode(verifyForgetPasswordDto.getEmail(), Integer.valueOf(verifyForgetPasswordDto.getEmailCode()))
                .orElseThrow(() -> new ForbiddenException("Your code is wrong"));
        user.setPassword(passwordEncoder.encode(verifyForgetPasswordDto.getNewPassword()));
        return ResponseEntity.ok(StandardResponse.builder().status(200).message("Successfully updated password").data(userRepository.save(user)).build());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new DataNotFoundException("User not found"));
    }
}

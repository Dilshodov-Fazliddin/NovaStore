package uz.nova.novastore.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.response.JwtResponse;
import uz.nova.novastore.domain.response.StandardResponse;
import uz.nova.novastore.domain.response.Status;
import uz.nova.novastore.domain.user.LoginDto;
import uz.nova.novastore.domain.user.UserCreateDto;
import uz.nova.novastore.entity.user.UserEntity;
import uz.nova.novastore.entity.user.UserState;
import uz.nova.novastore.entity.user.VerificationEntity;
import uz.nova.novastore.exception.DataNotFoundException;
import uz.nova.novastore.exception.NotAcceptableException;
import uz.nova.novastore.repository.verification.VerificationRepository;
import uz.nova.novastore.repository.role.UserRoleRepository;
import uz.nova.novastore.repository.user.UserRepository;
import uz.nova.novastore.service.mail.MailService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationRepository verificationRepository;
    private final MailService mailService;
    private final JwtService jwtService;
    private final Random random = new Random();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }


    public StandardResponse<UserEntity> signUp(UserCreateDto userDto) {
        UserEntity user = modelMapper.map(userDto, UserEntity.class);

        user.setAttempts(0);
        user.setRole(List.of(roleRepository.findUserRoleEntitiesByName("ROLE_USER")
                .orElseThrow(() -> new DataNotFoundException("Role not found"))));
        user.setState(UserState.NOT_VERIFIED);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));


        int i = random.nextInt(1000, 9999);
        UserEntity save = userRepository.save(user);
        verificationRepository.save(new VerificationEntity(user, i));
        mailService.sendVerifyCode(save);

        return StandardResponse.<UserEntity>builder()
                .status(Status.SUCCESS)
                .message("Successfully signed up")
                .data(save).build();
    }

    public StandardResponse<Boolean> verify(String code, UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        VerificationEntity verificationEntity = verificationRepository.findVerificationEntityByUserId(userId)
                .orElseThrow(() -> new DataNotFoundException("Code not found"));

        if (Objects.equals(verificationEntity.getCode().toString(),code)) {
            if (verificationEntity.getCreatedDate().plusMinutes(10).isAfter(LocalDateTime.now())) {
                verificationRepository.delete(verificationEntity);
                user.setState(UserState.ACTIVE);
                userRepository.save(user);
            } else {
                verificationRepository.delete(verificationEntity);
                int i = random.nextInt(1000, 9999);
                verificationRepository.save(new VerificationEntity(user, i));
                mailService.sendVerifyCode(user);
                throw new NotAcceptableException("verification code is expired");
            }
        } else {
            user.setAttempts(user.getAttempts() + 1);
            userRepository.save(user);
            throw new NotAcceptableException("Your code is wrong");
        }
        return StandardResponse.<Boolean>builder()
                .status(Status.SUCCESS)
                .message("Successfully verified")
                .data(true).build();
    }


    public StandardResponse<JwtResponse>login(LoginDto loginDto){
        UserEntity user = userRepository.findUserEntityByUsername(loginDto.getUsername())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (passwordEncoder.matches(loginDto.getPassword(),user.getPassword())){
                user.setAttempts(0);
                userRepository.save(user);
                return StandardResponse.<JwtResponse>builder()
                        .status(Status.SUCCESS)
                        .message("Success")
                        .data(JwtResponse.builder()
                                .accessToken(jwtService.generateAccessToken(user))
                                .refreshToken(jwtService.generateRefreshToken(user))
                                .build()).build();
        }else{
            user.setAttempts(user.getAttempts()+1);
            userRepository.save(user);

            if (user.getAttempts()>=3) {
                user.setState(UserState.BLOCK);
                userRepository.save(user);
                throw new NotAcceptableException("Your account has been blocked");
            }
            throw new NotAcceptableException("Your attempts are reaching 3, be careful, your account may be blocked");
        }
    }
}

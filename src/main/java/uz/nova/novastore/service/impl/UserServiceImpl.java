package uz.nova.novastore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.*;
import uz.nova.novastore.domain.request.ProfileDto;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.entity.UserEntity;
import uz.nova.novastore.exception.DataNotFoundException;
import uz.nova.novastore.exception.ForbiddenException;
import uz.nova.novastore.exception.NotAcceptableException;
import uz.nova.novastore.jwt.JwtService;
import uz.nova.novastore.mapper.UserMapper;
import uz.nova.novastore.repository.RoleRepository;
import uz.nova.novastore.repository.UserRepository;
import uz.nova.novastore.service.MailService;
import uz.nova.novastore.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
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
        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new DataNotFoundException("Password or username isn't correct"));
        if ((passwordEncoder.matches(loginDto.getPassword(),user.getPassword()))) {
            if (user.getIsEnabled()) {
                return ResponseEntity.ok(StandardResponse.builder().status(200).message("User successfully login in system").
                        data(JwtResponse.builder().accessToken(jwtService.generateAccessToken(user)).build())
                        .build());
            }
            throw new NotAcceptableException("Your account has blocked");
        }
        throw new NotAcceptableException("Password or username isn't correct");
    }

    @Override
    public ResponseEntity<StandardResponse<?>> forgetPassword(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found"));
        int code = new Random().nextInt(1000, 9999);
        user.setEmailCode(code);
        userRepository.save(user);
        mailService.sendVerifyCode(code, email);
        return ResponseEntity.ok(StandardResponse.builder().status(200).message("send verify code").data(null).build());
    }

    @Override
    public ResponseEntity<StandardResponse<?>> verifyForgetCode(VerifyForgetPasswordDto verifyForgetPasswordDto) {
        UserEntity user = userRepository.findByEmailAndEmailCode(verifyForgetPasswordDto.getEmail(), Integer.valueOf(verifyForgetPasswordDto.getEmailCode()))
                .orElseThrow(() -> new ForbiddenException("Your code is wrong"));
        userRepository.save(user);
        return ResponseEntity.ok(StandardResponse.builder().status(200).message("Your code is checked ✅").data(null).build());
    }

    @Override
    public ResponseEntity<StandardResponse<?>> verifyForgetSetNewPassword(String email,String password) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        user.setEmailCode(null);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok(StandardResponse.builder().status(200).message("User password saved").data(null).build());
    }

    @Override
    public ResponseEntity<StandardResponse<Object>> getAllUsers(int page,int size) {
        Sort sort = Sort.by(Sort.Direction.ASC,"firstname");
        Pageable pageable=PageRequest.of(page,size,sort);
        List<UserEntity> all = userRepository.findAll();
        Page<ProfileDto> userForFronts = mapUsers(all,pageable);

        return ResponseEntity.ok(StandardResponse.builder().data(userForFronts).message("This is user List").status(200).build());
    }

    @Override
    public ResponseEntity<StandardResponse<List<ProfileDto>>> getCustomers(int page,int size) {
        RoleEntity role = roleRepository.findByName("ROLE_CUSTOMER");
        Sort sort=Sort.by(Sort.Direction.ASC,"firstname");
        Pageable pageable = PageRequest.of(page,size,sort);
        List<UserEntity>customers=userRepository.findByRole(role);
        Page<ProfileDto> profileDto = mapUsers(customers, pageable);
        return ResponseEntity.ok(StandardResponse.<List<ProfileDto>>builder().status(200).message("This is customers").data(profileDto).build());
    }

    @Override
    public Page<ProfileDto> mapUsers(List<UserEntity> userEntities, Pageable pageable) {
        List<ProfileDto>userForFronts = new ArrayList<>();
        for (UserEntity users:userEntities){
            userForFronts.add(ProfileDto.builder()
                            .age(2023-users.getBirthday().getYear())
                            .firstname(users.getFirstname())
                            .lastName(users.getFirstname())
                    .build());
        }
        return new PageImpl<>(userForFronts,pageable,userEntities.size());
    }

    @Override
    public ResponseEntity<StandardResponse<?>> blockUsers(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found"));
        if (!user.getIsEnabled()){
            throw new NotAcceptableException("This user blocked");
        }else {
            user.setIsEnabled(false);
            mailService.sendNotificationBlockUser(user.getEmail());
            userRepository.save(user);
            return ResponseEntity.ok(StandardResponse.builder().status(200).message("account blocked successfully").data(null).build());
        }
    }

    @Override
    public ResponseEntity<StandardResponse<?>> unblockUsers(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found"));
        if (user.getIsEnabled()){
            throw new NotAcceptableException("This user not blocked");
        }else {
            user.setIsEnabled(true);
            mailService.sendNotificationUnBlockUser(user.getEmail());
            userRepository.save(user);
            return ResponseEntity.ok(StandardResponse.builder().status(200).message("account unblocked successfully").data(null).build());
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new DataNotFoundException("User not found"));
    }
}

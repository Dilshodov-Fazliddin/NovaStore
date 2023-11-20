package uz.nova.novastore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.UserCreateDto;
import uz.nova.novastore.entity.UserEntity;
import uz.nova.novastore.repository.UserRoleRepository;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity toEntity(UserCreateDto userCreate, Integer randomCode) {
        return UserEntity.builder()
                .email(userCreate.getEmail())
                .name(userCreate.getName())
                .role(userRoleRepository.findByName("ROLE_USER"))
                .emailCode(randomCode)
                .password(passwordEncoder.encode(userCreate.getPassword()))
                .isCredentialsNonExpired(true)
                .isEnabled(false)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .birthday(userCreate.getBirthDay()).build();
    }
}

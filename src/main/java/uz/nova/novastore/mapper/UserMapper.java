package uz.nova.novastore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.UserCreateDto;
import uz.nova.novastore.entity.UserEntity;
import uz.nova.novastore.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final RoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity toEntity(UserCreateDto userCreate, Integer randomCode) {
        return UserEntity.builder()
                .email(userCreate.getEmail())
                .firstname(userCreate.getFirstname())
                .lastname(userCreate.getLastname())
                .birthday(userCreate.getBirthday())
                .role(userRoleRepository.findByName("ROLE_USER"))
                .emailCode(randomCode)
                .password(passwordEncoder.encode(userCreate.getPassword()))
                .isCredentialsNonExpired(true)
                .isEnabled(false)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .birthday(userCreate.getBirthday()).build();
    }
}

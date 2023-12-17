package uz.nova.novastore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.nova.novastore.entity.CategoryEntity;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.entity.UserEntity;
import uz.nova.novastore.entity.enums.Category;
import uz.nova.novastore.repository.CategoryRepository;
import uz.nova.novastore.repository.UserRepository;
import uz.nova.novastore.repository.UserRoleRepository;

import java.time.LocalDate;


@Configuration
@RequiredArgsConstructor
public class DataLoaderConfig implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    @Value("${forDataLoader}")
    private String forDataLoader;

    @Override
    public void run(String... args) {
        if (forDataLoader.equals("always")) {
            for (Category value : Category.values()) {
                categoryRepository.save(CategoryEntity.builder().name(value.toString()).build());
            }
            System.out.println("Successfully saved basic categories");
            RoleEntity roleAdmin = userRoleRepository.save(RoleEntity.builder().name("ROLE_ADMIN").build());
            userRoleRepository.save(RoleEntity.builder().name("ROLE_USER").build());
            userRoleRepository.save(RoleEntity.builder().name("ROLE_CUSTOMER").build());
            System.out.println("Basic Role successfully saved");
            userRepository.save(UserEntity.builder().firstname("admin").lastname("admin").birthday(LocalDate.parse("2023-01-01")).email("admin@gmail.com").isCredentialsNonExpired(true).isAccountNonExpired(true).isAccountNonLocked(true).isEnabled(true).password(passwordEncoder.encode("admin")).role(roleAdmin).build());
            System.out.println("Admin saved");
        }
    }
}

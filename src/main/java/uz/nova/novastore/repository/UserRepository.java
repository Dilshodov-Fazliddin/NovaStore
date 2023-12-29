package uz.nova.novastore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nova.novastore.entity.BookingEntity;
import uz.nova.novastore.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    UserEntity searchByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmailAndEmailCode(String email, Integer emailCode);

}

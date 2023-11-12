package uz.nova.novastore.repository.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nova.novastore.entity.user.VerificationEntity;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, UUID> {
    Optional<VerificationEntity>findVerificationEntityByUserId(UUID userID);
}

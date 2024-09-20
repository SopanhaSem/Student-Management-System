package co.istad.sms.features.auth;

import co.istad.sms.domain.EmailVerification;
import co.istad.sms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification,Integer> {
    Optional<EmailVerification> findByUser(User user);
}

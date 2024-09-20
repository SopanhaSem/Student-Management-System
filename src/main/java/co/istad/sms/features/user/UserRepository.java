package co.istad.sms.features.user;

import co.istad.sms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
        Boolean existsByEmail(String email);
        Boolean existsByUsername(String username);
        Optional<User> findByEmail(String email);
}

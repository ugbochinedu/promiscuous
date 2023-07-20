package africa.semicolon.promiscuous.repositories;

import africa.semicolon.promiscuous.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

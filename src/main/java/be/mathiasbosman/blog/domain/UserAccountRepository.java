package be.mathiasbosman.blog.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository for the {@link UserAccount}.
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

  Optional<UserAccount> getByUsername(String username);

}

package be.mathiasbosman.blog.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogUserRepository extends CrudRepository<BlogUser, UUID> {

  Optional<BlogUser> findByUsername(String username);
}

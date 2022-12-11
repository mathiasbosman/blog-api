package be.mathiasbosman.blog.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface BlogUserRepository extends PagingAndSortingRepository<BlogUser, UUID>,
    CrudRepository<BlogUser, UUID> {

  Optional<BlogUser> findByUsername(String username);
}

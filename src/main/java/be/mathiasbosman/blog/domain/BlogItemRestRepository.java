package be.mathiasbosman.blog.domain;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "items", path = "items")
public interface BlogItemRestRepository extends
    PagingAndSortingRepository<BlogItem, UUID>, CrudRepository<BlogItem, UUID> {

  Page<BlogItem> findAllByFeatured(Pageable pageable,
      boolean featured); //todo test for unused warning
}

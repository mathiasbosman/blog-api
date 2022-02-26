package be.mathiasbosman.blog.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Paging repository for {@link BlogItem}.
 */
public interface BlogItemRepository extends PagingAndSortingRepository<BlogItem, UUID> {

  List<BlogItem> findAllByDeleted(boolean deleted, Pageable page);

  long countAllByDeleted(boolean deleted);

}

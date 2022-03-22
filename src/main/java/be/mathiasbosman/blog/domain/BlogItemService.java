package be.mathiasbosman.blog.domain;

import be.mathiasbosman.blog.security.SecurityContext;
import be.mathiasbosman.blog.security.SecurityContext.Authority;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service mainly for the {@link BlogItem} entity.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BlogItemService {

  private final BlogItemRepository repository;

  @Transactional(readOnly = true)
  public Optional<BlogItem> getItem(UUID uuid) {
    return repository.findById(uuid);
  }

  @Transactional(readOnly = true)
  public List<BlogItem> getItems(boolean deleted, int page, int amount) {
    Pageable pageable = PageRequest.of(page, amount, BlogItem.SORT_BY_DATE);
    return repository.findAllByDeleted(deleted, pageable);
  }

  @Transactional(readOnly = true)
  public long countItems(boolean deleted) {
    return repository.countAllByDeleted(deleted);
  }

  @Transactional
  @PreAuthorize("hasAuthority('blog-write')")
  public BlogItem saveNewItem(String title, String content, UUID posterId) {
    BlogItem item = BlogItem.builder()
        .title(title)
        .content(content)
        .posterId(posterId)
        .build();
    log.trace("Saving {}", item);
    return repository.save(item);
  }

  @Transactional
  public BlogItem updateItem(UUID id, String title, String content) {
    return repository.findById(id)
        .map(item -> {
          BlogItem updatedItem = item.toBuilder()
              .title(title)
              .content(content)
              .build();
          log.trace("Updating {}", updatedItem);
          return repository.save(updatedItem);
        }).orElseThrow();
  }

  @Transactional
  public BlogItem deleteItem(UUID id) {
    return repository.findById(id)
        .map(item -> {
          BlogItem deletedItem = item.toBuilder()
              .deleted(true)
              .build();
          log.trace("Deleting {}", deletedItem);
          return repository.save(deletedItem);
        }).orElseThrow();
  }
}

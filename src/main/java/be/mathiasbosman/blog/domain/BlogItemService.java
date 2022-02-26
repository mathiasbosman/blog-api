package be.mathiasbosman.blog.domain;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BlogItemService {

  private final BlogItemRepository repository;

  @Transactional(readOnly = true)
  public BlogItem getItem(UUID uuid) {
    return repository.findById(uuid).orElseThrow();
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
  public BlogItem saveNewItem(String title, String content) {
    BlogItem item = BlogItem.builder().title(title).content(content).build();
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

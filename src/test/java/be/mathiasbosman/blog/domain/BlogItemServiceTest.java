package be.mathiasbosman.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BlogItemServiceTest extends AbstractSpringBootTest {

  @Autowired
  private BlogItemRepository repository;
  @Autowired
  private BlogItemService service;

  @AfterEach
  @BeforeEach
  void tearDown() {
    repository.deleteAll();
  }

  @Test
  void getItem() {
    BlogItem item = BlogItem.builder().title("foo bar").build();
    UUID randomId = UUID.randomUUID();

    repository.save(item);

    assertThat(service.getItem(item.getId())).isNotNull();
    assertThat(item.toString()).contains("id=", "created=", "updated=", "title=", "deleted=");
    assertThrows(NoSuchElementException.class, () -> service.getItem(randomId));
  }

  @Test
  void getItems() {
    List<UUID> deletedItemIds = createBlogItems(2, true);
    List<UUID> publishedItemIds = createBlogItems(25, false);

    List<BlogItem> deletedResult = service.getItems(true, 0, 10);
    List<BlogItem> publishedResult = service.getItems(false, 0, 10);

    assertThat(deletedResult)
        .hasSize(2)
        .allSatisfy(item -> assertThat(item.getId()).isIn(deletedItemIds));
    assertThat(publishedResult)
        .hasSize(10)
        .allSatisfy(item -> assertThat(item.getId()).isIn(publishedItemIds));

    List<BlogItem> pagedResult = service.getItems(false, 2, 10);

    assertThat(pagedResult)
        .hasSize(5)
        .allSatisfy(item -> assertThat(item.getTitle()).containsAnyOf("0", "1", "2", "3", "4"));
  }

  @Test
  void countItems() {
    createBlogItems(3, true);
    createBlogItems(5, false);

    assertThat(service.countItems(true)).isEqualTo(3);
    assertThat(service.countItems(false)).isEqualTo(5);
  }

  @Test
  void saveItem() {
    BlogItem blogItem = service.saveNewItem("foo", "bar");

    assertThat(blogItem.getId()).isNotNull();
    assertThat(blogItem.getTitle()).isEqualTo("foo");
    assertThat(blogItem.getContent()).isEqualTo("bar");
    assertThat(blogItem.isDeleted()).isFalse();
    assertThat(blogItem.getCreated()).isNotNull();
    assertThat(blogItem.getUpdated()).isNotNull();
  }

  @Test
  void updateItem() {
    BlogItem blogItem = BlogItem.builder().title("foo").content("bar").build();
    repository.save(blogItem);

    BlogItem updatedItem = service.updateItem(blogItem.getId(), "oof", "rab");

    assertThat(updatedItem.getTitle()).isEqualTo("oof");
    assertThat(updatedItem.getContent()).isEqualTo("rab");
  }

  @Test
  void deleteItem() {
    BlogItem blogItem = BlogItem.builder().title("foo").content("bar").build();
    repository.save(blogItem);

    BlogItem deletedItem = service.deleteItem(blogItem.getId());

    assertThat(deletedItem.isDeleted()).isTrue();
  }

  private List<UUID> createBlogItems(int amount, boolean deleted) {
    List<BlogItem> savedItems = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      savedItems.add(BlogItem.builder().title("foo bar " + i).deleted(deleted).build());
    }
    return savedItems.stream().map((item) -> repository.save(item).getId()).toList();
  }

}
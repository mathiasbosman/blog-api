package be.mathiasbosman.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class BlogItemRepositoryTest extends AbstractSpringBootTest {

  @Autowired
  private BlogItemRepository repository;

  @BeforeEach
  @AfterEach
  void tearDown() {
    repository.deleteAll();
  }

  @Test
  void save() {
    BlogItem blogItem = BlogItem.builder().title("foo bar").content("lorem ipsum").build();

    BlogItem savedItem = repository.save(blogItem);

    assertThat(savedItem.getTitle()).isEqualTo(blogItem.getTitle());
    assertThat(savedItem.getContent()).isEqualTo(blogItem.getContent());
    assertThat(savedItem.isDeleted()).isEqualTo(blogItem.isDeleted());
    assertThat(savedItem.getCreated()).isNotNull();
    assertThat(savedItem.getUpdated()).isNotNull();
  }

  @Test
  void findAllByDeleted() {
    BlogItem blogItemA = BlogItem.builder().title("foo bar").deleted(false).build();
    BlogItem blogItemB = BlogItem.builder().title("foo bar 2").deleted(true).build();

    repository.save(blogItemA);
    repository.save(blogItemB);

    List<BlogItem> resultA = repository.findAllByDeleted(false, Pageable.unpaged());
    assertThat(resultA).hasSize(1);
    assertThat(resultA.get(0).getId()).isEqualTo(blogItemA.getId());

    List<BlogItem> resultB = repository.findAllByDeleted(true, Pageable.unpaged());
    assertThat(resultB).hasSize(1);
    assertThat(resultB.get(0).getId()).isEqualTo(blogItemB.getId());
  }

  @Test
  void countByDeleted() {
    assertThat(repository.countAllByDeleted(true)).isZero();
    assertThat(repository.countAllByDeleted(false)).isZero();

    BlogItem blogItemA = BlogItem.builder().title("foo bar").deleted(false).build();
    BlogItem blogItemB = BlogItem.builder().title("foo bar 2").deleted(true).build();

    repository.save(blogItemA);
    repository.save(blogItemB);

    assertThat(repository.countAllByDeleted(true)).isOne();
    assertThat(repository.countAllByDeleted(false)).isOne();
  }
}
package be.mathiasbosman.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

class BlogItemRepositoryTest extends AbstractSpringBootTest {

  @Autowired
  private BlogItemRepository blogItemRepository;

  @AfterEach
  void tearDown() {
    blogItemRepository.deleteAll();
  }

  @Test
  void save() {
    BlogItem blogItem = BlogItemMother.blogItem("foo", "bar", false);

    BlogItem savedItem = blogItemRepository.save(blogItem);

    assertThat(savedItem.getTitle()).isEqualTo(blogItem.getTitle());
    assertThat(savedItem.getContent()).isEqualTo(blogItem.getContent());
    assertThat(savedItem.isDeleted()).isEqualTo(blogItem.isDeleted());
    assertThat(savedItem.getCreated()).isNotNull();
    assertThat(savedItem.getUpdated()).isNotNull();
  }

  @Test
  void findAllByDeleted() {
    BlogItem blogItemA = BlogItemMother.blogItem("foo", "bar",false);
    BlogItem blogItemB = BlogItemMother.blogItem("foo 2", "bar",true);

    blogItemRepository.save(blogItemA);
    blogItemRepository.save(blogItemB);

    List<BlogItem> resultA = blogItemRepository.findAllByDeleted(false, Pageable.unpaged());
    assertThat(resultA).hasSize(1);
    assertThat(resultA.get(0).getId()).isEqualTo(blogItemA.getId());

    List<BlogItem> resultB = blogItemRepository.findAllByDeleted(true, Pageable.unpaged());
    assertThat(resultB).hasSize(1);
    assertThat(resultB.get(0).getId()).isEqualTo(blogItemB.getId());
  }

  @Test
  void countByDeleted() {
    System.out.println(UUID.randomUUID());
    assertThat(blogItemRepository.countAllByDeleted(true)).isZero();
    assertThat(blogItemRepository.countAllByDeleted(false)).isZero();

    BlogItem blogItemA = BlogItemMother.blogItem("foo", "bar", false);
    BlogItem blogItemB = BlogItemMother.blogItem("foo", "bar", true);

    blogItemRepository.save(blogItemA);
    blogItemRepository.save(blogItemB);

    assertThat(blogItemRepository.countAllByDeleted(true)).isOne();
    assertThat(blogItemRepository.countAllByDeleted(false)).isOne();
  }
}
package be.mathiasbosman.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

class BlogItemRestRepositoryTest extends AbstractSpringBootTest {

  @Autowired
  private BlogItemRestRepository repository;

  @Test
  void findAllByFeatured() {
    BlogUser user = entityManager.persist(BlogUserMother.randomBlogUser());
    entityManager.persist(BlogItemMother.randomBlogItem(user));
    entityManager.persist(BlogItemMother.randomBlogItem(user).toBuilder().featured(true).build());

    assertThat(repository.findAllByFeatured(Pageable.unpaged(), false).getTotalElements())
        .isEqualTo(1);
    assertThat(repository.findAllByFeatured(Pageable.unpaged(), true).getTotalElements())
        .isEqualTo(1);
  }
}
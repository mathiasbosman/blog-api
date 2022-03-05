package be.mathiasbosman.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserAccountRepositoryTest extends AbstractSpringBootTest {

  @Autowired
  private UserAccountRepository repository;

  @Test
  void getByUsername() {
    repository.save(UserAccountMother.user("foo", "bar"));

    Optional<UserAccount> existinguser = repository.getByUsername("foo");
    Optional<UserAccount> noneExistingUser = repository.getByUsername("null");

    assertThat(existinguser).isNotEmpty();
    assertThat(noneExistingUser).isEmpty();
  }

}

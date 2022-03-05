package be.mathiasbosman.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import be.mathiasbosman.blog.security.SecurityContext.Authority;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserAccountServiceTest extends AbstractSpringBootTest {

  @Autowired
  private UserAccountService accountService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void adminUserPresentAfterConstruct() {
    assertThat(accountService.getByUsername(UserAccountService.DEFAULT_USER)).isNotEmpty();
  }

  @Test
  void saveNewAccount() {
    UserAccount userAccount = accountService.saveNewAccount("foo", "bar",
        Set.of(Authority.API_USER, Authority.BLOG_WRITE));

    assertThat(userAccount.getId()).isNotNull();
    assertThat(userAccount.getUsername()).isEqualTo("foo");
    assertThat(userAccount.getRoles()).contains(
        Authority.API_USER.name(), Authority.BLOG_WRITE.name());
    assertThat(passwordEncoder.matches("bar", userAccount.getPassword())).isTrue();
  }
}
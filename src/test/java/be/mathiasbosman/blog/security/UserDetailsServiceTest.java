package be.mathiasbosman.blog.security;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import be.mathiasbosman.blog.domain.BlogUser;
import be.mathiasbosman.blog.security.SecurityContext.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserDetailsServiceTest extends AbstractSpringBootTest {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void loadUserByUsername_fails() {
    assertThatThrownBy(() -> userDetailsService.loadUserByUsername("foo"))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessageContaining("foo");
  }

  @Test
  void loadUserByUsername() {
    BlogUser user = BlogUser.builder()
        .username("foo")
        .password(passwordEncoder.encode("bar"))
        .build();
    entityManager.persist(user);

    UserDetails userDetails = userDetailsService.loadUserByUsername("foo");

    assertThat(userDetails).isNotNull();
    assertThat(userDetails.getUsername()).isEqualTo("foo");
    assertThat(userDetails.getAuthorities())
        .hasSize(1)
        .first()
        .satisfies(auth -> assertThat(auth.getAuthority()).contains(Role.USER.name()));
  }
}

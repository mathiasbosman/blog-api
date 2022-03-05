package be.mathiasbosman.blog.security;

import be.mathiasbosman.blog.domain.UserAccount;
import be.mathiasbosman.blog.domain.UserAccountRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Basic HTTP authentication service.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

  private final UserAccountRepository userAccountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAccount userAccount = userAccountRepository.getByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("Account with username " + username + " not found"));

    return createSpringUser(userAccount);
  }

  private User createSpringUser(UserAccount account) {
    return new User(account.getUsername(), account.getPassword(), createAuthorities(account));
  }

  private List<SimpleGrantedAuthority> createAuthorities(UserAccount u) {
    if (!StringUtils.hasLength(u.getRoles())) {
      return Collections.emptyList();
    }
    return Arrays.stream(u.getRoles().split(";"))
        .map(SimpleGrantedAuthority::new)
        .toList();
  }

}

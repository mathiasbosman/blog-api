package be.mathiasbosman.blog.security;

import be.mathiasbosman.blog.domain.BlogUser;
import be.mathiasbosman.blog.domain.BlogUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final BlogUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(this::toSpringUser)
        .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
  }

  private User toSpringUser(BlogUser blogUser) {
    return new User(blogUser.getUsername(), blogUser.getPassword(), List.of(
        SecurityContext.basicAuthority()
    ));
  }
}

package be.mathiasbosman.blog.configuration;

import be.mathiasbosman.blog.domain.BlogUser;
import be.mathiasbosman.blog.domain.BlogUserRepository;
import jakarta.annotation.PostConstruct;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Profile("!test")
@RequiredArgsConstructor
public class BlogConfigService {

  private final BlogUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  @Transactional
  public void initAdminUser() {
    if (0 < userRepository.count()) {
      return;
    }
    String username = "admin";
    String password = UUID.randomUUID().toString();
    log.warn("No users found. Creating default user with username: {} and password: {}",
        username, password);
    userRepository.save(
        BlogUser.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .build()
    );
  }
}

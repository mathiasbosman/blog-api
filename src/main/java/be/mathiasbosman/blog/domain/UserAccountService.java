package be.mathiasbosman.blog.domain;

import be.mathiasbosman.blog.security.SecurityContext.Authority;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service mainly for the {@link UserAccount} entity.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountService {

  public static final String DEFAULT_USER = "admin";

  private final UserAccountRepository userAccountRepository;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void initializeAdminUser() {
    if (0 >= userAccountRepository.count()) {
      log.warn("No users found. Creating new user ({})", DEFAULT_USER);
      saveNewAccount(DEFAULT_USER, DEFAULT_USER, Collections.singletonList(Authority.API_USER));
    }
  }

  @Transactional(readOnly = true)
  public Optional<UserAccount> getByUsername(@NonNull String username) {
    return userAccountRepository.getByUsername(username);
  }

  @Transactional
  public UserAccount saveNewAccount(@NonNull String username, @NonNull String password,
      Collection<Authority> roles) {
    UserAccount account = UserAccount.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .roles(roles.stream().map(Enum::name).collect(Collectors.joining(";")))
        .build();
    log.trace("Saving {}", account);
    return userAccountRepository.save(account);
  }
}

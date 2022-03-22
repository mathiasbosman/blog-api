package be.mathiasbosman.blog.security;

import be.mathiasbosman.blog.domain.BlogException;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.event.Level;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security context; holding things such as an authority enum.
 */
public class SecurityContext {

  @Getter
  @RequiredArgsConstructor
  public enum Authority {
    API_USER("api-user"),
    BLOG_WRITE("blog-write");

    private final String value;
  }

  public static Optional<String> getUsername() {
    return getAuthentication()
        .flatMap(SecurityContext::accessToken)
        .map(AccessToken::getPreferredUsername);
  }

  public static Optional<String> getUserId() {
    return getAuthentication()
        .flatMap(SecurityContext::accessToken)
        .map(AccessToken::getId);
  }

  public static UUID getUserUuid() {
    return getUserId().map(UUID::fromString).orElseThrow(() -> new BlogException(Level.ERROR,
        "No user ID found in the security context"));
  }

  private static Optional<AccessToken> accessToken(Authentication auth) {
    if (auth instanceof KeycloakAuthenticationToken token && auth.getDetails() instanceof SimpleKeycloakAccount) {
      return Optional.of(token.getAccount().getKeycloakSecurityContext().getToken());
    }
    return Optional.empty();
  }

  private static Optional<Authentication> getAuthentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
  }
}

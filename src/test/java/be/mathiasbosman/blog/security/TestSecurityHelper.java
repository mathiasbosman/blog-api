package be.mathiasbosman.blog.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.mathiasbosman.blog.security.SecurityContext.Authority;
import java.security.Principal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TestSecurityHelper {

  public void setSecurityContextForBlogWriter() {
    setSecurityContextWithRoles(Set.of(Authority.BLOG_WRITE));
  }

  public void setSecurityContextWithRoles(Set<Authority> authorities) {
    KeycloakAccount keycloakAccount = new SimpleKeycloakAccount(mockPrincipal(), null,
        mockContext(mockToken()));
    Set<SimpleGrantedAuthority> simpleAuthorities = authorities.stream().map(
        authority -> new SimpleGrantedAuthority(authority.getValue())
    ).collect(Collectors.toSet());
    KeycloakAuthenticationToken keycloakAuthenticationToken = new KeycloakAuthenticationToken(
        keycloakAccount, false, simpleAuthorities);
    setSecurityContext(keycloakAuthenticationToken);
  }

  private static Principal mockPrincipal() {
    Principal principal = mock(Principal.class);
    when(principal.getName()).thenReturn("username");
    return principal;
  }

  private static RefreshableKeycloakSecurityContext mockContext(AccessToken token) {
    RefreshableKeycloakSecurityContext context = mock(RefreshableKeycloakSecurityContext.class);
    when(context.getToken()).thenReturn(token);
    return context;
  }

  private static AccessToken mockToken() {
    AccessToken token = mock(AccessToken.class);
    when(token.getId()).thenReturn(UUID.randomUUID().toString());
    return token;
  }

  public static void setSecurityContext(Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public void clearContext() {
    SecurityContextHolder.clearContext();
  }
}

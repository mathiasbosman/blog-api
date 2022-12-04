package be.mathiasbosman.blog.security;

import be.mathiasbosman.blog.domain.BlogException;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

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

  public static String getUserId() {
    return getAttribute(GithubAttribute.ID);
  }

  public static String getUsername() {
    return getAttribute(GithubAttribute.USERNAME);
  }

  public static String getClientId() {
    return getAuthentication()
        .flatMap(SecurityContext::token)
        .map(OAuth2AuthenticationToken::getAuthorizedClientRegistrationId)
        .orElseThrow(() -> new BlogException(Level.ERROR, "Could not determine OAuth client id"));
  }

  private static String getAttribute(GithubAttribute attribute) {
    Map<String, Object> attributes = getAttributes();
    if (attributes.containsKey(attribute.getAttributeKey())) {
      return attributes.get(attribute.getAttributeKey()).toString();
    }
    throw new BlogException(Level.ERROR, "Could not determine OAuth attribute %s", attribute);
  }

  private static Map<String, Object> getAttributes() {
    return getAuthentication()
        .flatMap(SecurityContext::principal)
        .map(OAuth2AuthenticatedPrincipal::getAttributes)
        .orElseThrow(() -> new BlogException(Level.ERROR,
            "No OAuth2 attributes found in the security context"));

  }

  private static Optional<OAuth2User> principal(Authentication auth) {
    return token(auth)
        .map(OAuth2AuthenticationToken::getPrincipal);
  }

  private static Optional<OAuth2AuthenticationToken> token(Authentication auth) {
    if (auth instanceof OAuth2AuthenticationToken token) {
      return Optional.of(token);
    }
    return Optional.empty();
  }

  @Getter
  @RequiredArgsConstructor
  public enum GithubAttribute {
    USERNAME("login"),
    ID("id"),
    AVATAR_URL("avatar_url"),
    NAME("name");
    private final String attributeKey;

    public static String nameAttributeKey() {
      return NAME.getAttributeKey();
    }
  }

  private static Optional<Authentication> getAuthentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
  }
}

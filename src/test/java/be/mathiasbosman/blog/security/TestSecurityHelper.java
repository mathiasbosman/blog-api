package be.mathiasbosman.blog.security;


import be.mathiasbosman.blog.security.SecurityContext.GithubAttribute;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class TestSecurityHelper {

  public static final String OAUTH_CLIENT_ID = "testClient";

  public void setSecurityContext(String name, String id) {
    var principal = new DefaultOAuth2User(null,
        Map.of(GithubAttribute.USERNAME.getAttributeKey(), name,
            GithubAttribute.NAME.getAttributeKey(), name,
            GithubAttribute.ID.getAttributeKey(), id),
        GithubAttribute.nameAttributeKey());
    SecurityContextHolder.getContext()
        .setAuthentication(new OAuth2AuthenticationToken(principal, null, OAUTH_CLIENT_ID));
  }

  public void clearContext() {
    SecurityContextHolder.clearContext();
  }
}

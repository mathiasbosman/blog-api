package be.mathiasbosman.blog.domain;

import be.mathiasbosman.blog.security.TestSecurityHelper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OAuth2UserMother {

  public static OAuth2User oauth2user() {
    return new OAuth2User("foo", TestSecurityHelper.OAUTH_CLIENT_ID, "extId", "bar");
  }
}

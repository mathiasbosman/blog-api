package be.mathiasbosman.blog.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityContext {

  private SecurityContext() {
    // util class
  }

  public static GrantedAuthority basicAuthority() {
    return new SimpleGrantedAuthority(Role.USER.name());
  }

  public enum Role {
    USER
  }
}

package be.mathiasbosman.blog.security;

/**
 * Security context; holding things such as an authority enum.
 */
public class SecurityContext {
  public enum Authority {
    API_USER,
    BLOG_WRITE
  }
}

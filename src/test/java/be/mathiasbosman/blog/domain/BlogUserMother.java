package be.mathiasbosman.blog.domain;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BlogUserMother {

  public static BlogUser randomBlogUser() {
    return BlogUser.builder()
        .username("foo " + UUID.randomUUID())
        .password("bar " + UUID.randomUUID())
        .build();
  }
}

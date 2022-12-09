package be.mathiasbosman.blog.domain;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BlogItemMother {

  public static BlogItem randomBlogItem() {
    return BlogItem.builder()
        .title("foo bar " + UUID.randomUUID())
        .content("lorem ipsum " + UUID.randomUUID())
        .posterId(UUID.randomUUID())
        .deleted(false)
        .build();
  }

}

package be.mathiasbosman.blog.domain;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BlogItemMother {

  public static BlogItem blogItem() {
    return blogItem("foo", "bar", UUID.randomUUID(), false);
  }

  public static BlogItem blogItem(String title, String content, boolean deleted) {
    return blogItem(title, content, UUID.randomUUID(), deleted);
  }

  public static BlogItem blogItem(String title, String content, UUID posterId,
      boolean deleted) {
    return BlogItem.builder()
        .title(title)
        .content(content)
        .deleted(deleted)
        .posterId(posterId)
        .build();
  }
}

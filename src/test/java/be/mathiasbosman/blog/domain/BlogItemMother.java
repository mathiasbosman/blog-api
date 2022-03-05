package be.mathiasbosman.blog.domain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BlogItemMother {

  public static BlogItem blogItem(String title, String content) {
    return BlogItem.builder()
        .title(title)
        .content(content)
        .deleted(false)
        .build();
  }

  public static BlogItem blogItem() {
    return blogItem("foo", "bar");
  }
}

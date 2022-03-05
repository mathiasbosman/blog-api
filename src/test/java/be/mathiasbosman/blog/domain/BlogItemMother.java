package be.mathiasbosman.blog.domain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BlogItemMother {

  public static BlogItem blogItem(UserAccount user) {
    return blogItem("foo", "bar", user, false);
  }

  public static BlogItem blogItem(String title, String content, UserAccount userAccount,
      boolean deleted) {
    return BlogItem.builder()
        .title(title)
        .content(content)
        .deleted(deleted)
        .poster(userAccount)
        .build();
  }
}

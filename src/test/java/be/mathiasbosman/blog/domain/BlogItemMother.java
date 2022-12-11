package be.mathiasbosman.blog.domain;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BlogItemMother {

  public static BlogItem randomBlogItem(BlogUser poster) {
    UUID uuid = UUID.randomUUID();
    return BlogItem.builder()
        .title("foo bar " + uuid)
        .excerpt("excerpt - lorem ipsum" + uuid)
        .content("lorem ipsum " + uuid)
        .poster(poster)
        .build();
  }

}

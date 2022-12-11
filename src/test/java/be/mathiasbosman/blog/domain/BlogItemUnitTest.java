package be.mathiasbosman.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class BlogItemUnitTest {

  @Test
  void buildShortUrl() {
    BlogItem item = BlogItem.builder()
        .title("this is a none safe url, because it's got some strange #tags")
        .build();

    item.prePersist();

    String formattedDate = item.getCreated().format(DateTimeFormatter.ISO_DATE);
    assertThat(item.getPermalink())
        .isEqualTo(formattedDate + "-this-is-a-none-safe-url-because-its-got-some-strange-tags");
  }
}
package be.mathiasbosman.blog.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlogItemRecord(String title, String content, boolean isDeleted, UUID uuid,
                             LocalDateTime created, LocalDateTime updated) {

  public BlogItemRecord(String title, String content) {
    this(title, content, false, null, null, null);
  }

  public static BlogItemRecord fromEntity(BlogItem item) {
    return new BlogItemRecord(item.getTitle(), item.getContent(), item.isDeleted(), item.getId(),
        item.getCreated(), item.getUpdated());
  }
}

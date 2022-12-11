package be.mathiasbosman.blog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(callSuper = true, of = {"title", "featured"})
public class BlogItem extends AbstractAuditedEntity {

  @NotNull
  private String title;

  private String excerpt;

  @NotNull
  private String content;

  private boolean featured;

  @NotNull
  @ManyToOne
  private BlogUser poster;

  private String permalink;

  @Override
  void prePersist() {
    super.prePersist();
    permalink = buildPermalink();
  }

  String buildPermalink() {
    String safeTitle = getTitle()
        .replaceAll(" ", "-")
        .replaceAll("[^a-zA-Z0-9-]", "");
    return String.format("%s-%s",
        getCreated().format(DateTimeFormatter.ISO_DATE),
        safeTitle);
  }
}

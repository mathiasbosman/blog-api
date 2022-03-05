package be.mathiasbosman.blog.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Sort;

/**
 * The blog item entity.
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(callSuper = true, of = {"title", "deleted", "poster"})
public class BlogItem extends AbstractAuditedEntity implements Identifiable<UUID> {

  public static final Sort SORT_BY_DATE = Sort.by("created").descending();

  private String title;
  private String content;
  private boolean deleted;
  @ManyToOne
  private UserAccount poster;
}

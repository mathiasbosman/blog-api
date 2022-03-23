package be.mathiasbosman.blog.domain;

import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * Abstract entity holding the primary key.
 */
@Getter
@ToString(of = "id")
@MappedSuperclass
public abstract class AbstractEntity implements Identifiable<UUID> {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "uuid2")
  private UUID id;
}

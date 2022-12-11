package be.mathiasbosman.blog.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Getter
@ToString(of = "id")
@MappedSuperclass
public abstract class AbstractEntity implements Identifiable<UUID> {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "uuid2")
  private UUID id;
}

package be.mathiasbosman.blog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@MappedSuperclass
@ToString(callSuper = true)
public abstract class AbstractAuditedEntity extends AbstractEntity {

  @Column(updatable = false)
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime created;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime updated;

  @PrePersist
  void prePersistHandler() {
    created = LocalDateTime.now();
    updated = created;
    prePersist();
  }

  @PreUpdate
  void preUpdate() {
    updated = LocalDateTime.now();
  }

  void prePersist() {
    created = LocalDateTime.now();
    updated = created;
  }
}

package be.mathiasbosman.blog.domain;

import java.time.LocalDateTime;

/**
 * An auditable entity holding its creation and updated timestamps. They can be used with
 * annotations such as {@link javax.persistence.PrePersist} and {@link javax.persistence.PreUpdate}
 * or set manually.
 */
public interface AuditableEntity {

  /**
   * Returns the {@link LocalDateTime} at which the entity was created.
   *
   * @return the timestamp of creation
   */
  LocalDateTime getCreated();

  /**
   * Returns the {@link LocalDateTime} at which the entity was updated.
   *
   * @return the timestamp of update
   */
  LocalDateTime getUpdated();
}

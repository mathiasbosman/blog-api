package be.mathiasbosman.blog.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogUser extends AbstractAuditedEntity {

  @NotNull
  private String username;

  @NotNull
  private String password;

}

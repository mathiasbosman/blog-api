package be.mathiasbosman.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@JsonIgnoreProperties({"created", "updated"})
@NoArgsConstructor
@AllArgsConstructor
public class BlogUser extends AbstractAuditedEntity {

  @NotNull
  private String username;

  @NotNull
  @JsonIgnore
  private String password;

}

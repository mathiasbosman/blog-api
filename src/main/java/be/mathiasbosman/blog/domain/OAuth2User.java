package be.mathiasbosman.blog.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2User extends AbstractAuditedEntity {

  private String username;
  private String clientId;
  private String externalId;
  private String name;
}

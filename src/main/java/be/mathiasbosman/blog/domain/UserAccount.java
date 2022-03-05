package be.mathiasbosman.blog.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The user account entity.
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(callSuper = true, of = {"username", "roles"})
public class UserAccount extends AbstractEntity {

  private String username;
  private String password;
  private String roles;
}

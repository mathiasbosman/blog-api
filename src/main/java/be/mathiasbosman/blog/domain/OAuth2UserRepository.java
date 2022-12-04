package be.mathiasbosman.blog.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface OAuth2UserRepository extends CrudRepository<OAuth2User, UUID> {

  OAuth2User getByClientIdAndExternalId(String clientId, String externalId);
}

package be.mathiasbosman.blog.domain;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserService {

  private final OAuth2UserRepository repository;

  @Transactional(readOnly = true)
  public UUID getUuid(String clientId, String externalId) {
    return repository.getByClientIdAndExternalId(clientId, externalId).getId();
  }
}

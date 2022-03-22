package be.mathiasbosman.blog.security;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Keycloak configuration
 */
@Configuration
public class KeycloakConfig {

  /**
   * Bean to configure KeycloakConfigResolver to use properties file instead of keycloak.json
   * @return {@link KeycloakConfigResolver}
   */
  @Bean
  public KeycloakConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }
}

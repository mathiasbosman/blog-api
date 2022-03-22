package be.mathiasbosman.blog.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class SecurityController {

  private final RestTemplate restTemplate = new RestTemplate();
  private final KeycloakSpringBootProperties keycloakProps;

  @PostMapping("/auth/token")
  public ResponseEntity<Object> getToken(@RequestBody @NonNull UserDto userDto) {

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("grant_type", "password");
    parameters.add("client_id", keycloakProps.getResource());
    parameters.add("username", userDto.username());
    parameters.add("password", userDto.password());

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, httpHeaders);
    return restTemplate.exchange(buildTokenUri(), HttpMethod.POST,
        request, Object.class);
  }

  private String buildTokenUri() {
    return String.join("/", keycloakProps.getAuthServerUrl(), "realms",
        keycloakProps.getRealm(), "protocol/openid-connect/token");
  }
}

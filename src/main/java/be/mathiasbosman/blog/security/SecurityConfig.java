package be.mathiasbosman.blog.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the REST Api.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(@NonNull HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/rest/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .oauth2Login();

    return http.build();
  }
}

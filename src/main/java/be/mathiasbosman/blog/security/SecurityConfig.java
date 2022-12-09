package be.mathiasbosman.blog.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Security configuration for the REST Api.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements RepositoryRestConfigurer {

  @Bean
  public SecurityFilterChain filterChain(@NonNull HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers(HttpMethod.GET).permitAll()
        .anyRequest().authenticated()
        .and()
        .oauth2Login();

    return http.build();
  }

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
      CorsRegistry cors) {
    cors.addMapping("/**")
        .allowedOrigins("http://localhost:3000");
    RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
  }
}

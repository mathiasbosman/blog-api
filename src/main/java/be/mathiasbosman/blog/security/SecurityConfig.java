package be.mathiasbosman.blog.security;

import be.mathiasbosman.blog.security.SecurityContext.Authority;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration for the REST Api.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/rest/public/**").permitAll()
        .antMatchers("/rest/**").hasAuthority(Authority.API_USER.name())
        .antMatchers("/actuator/**").permitAll()
        .anyRequest().denyAll();
  }

}

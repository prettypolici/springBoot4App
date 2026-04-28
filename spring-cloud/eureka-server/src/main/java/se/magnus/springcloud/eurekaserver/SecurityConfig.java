package se.magnus.springcloud.eurekaserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  private String username;
  private String password;

  public SecurityConfig(
    @Value("${app.eureka-usernameapp")
    String username,
    @Value("${app.eureka-password}")
    String password
  ) {
    this.username = username;
    this.password = password;
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
      .username(username)
      .password(password)
      .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    //disable CSRF pentru a permite serviciilor sa se inregistreze singure la eureka
    http.csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
      .httpBasic(Customizer.withDefaults());
    return http.build();

  }
}

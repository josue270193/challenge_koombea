package app.josue.challenge_koombea.config;

import app.josue.challenge_koombea.security.JwtTokenAuthenticationFilter;
import app.josue.challenge_koombea.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Autowired
  private ReactiveUserDetailsService reactiveUserDetailsService;

  @Autowired
  private JwtTokenProvider tokenProvider;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
        .csrf(CsrfSpec::disable)
        .cors(corsSpec -> corsSpec.configurationSource(corsConfiguration()))
        .httpBasic(HttpBasicSpec::disable)
        .authenticationManager(customAuthenticationManager())
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers(POST, "/user/login").permitAll()
            .pathMatchers(POST, "/user/register").permitAll()
            .anyExchange().authenticated()
        )
        .addFilterBefore(new JwtTokenAuthenticationFilter(tokenProvider),
            SecurityWebFiltersOrder.HTTP_BASIC)
    ;
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfiguration() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(Arrays.asList("*"));
    corsConfig.setMaxAge(3600L);
    corsConfig.addAllowedMethod("*");
    corsConfig.addAllowedHeader("*");

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return source;
  }
  @Bean
  public ReactiveAuthenticationManager customAuthenticationManager() {
    var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
    authenticationManager.setPasswordEncoder(passwordEncoder());
    return authenticationManager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}

package app.josue.challenge_koombea.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

  private String secretKey = "default";

  private long validityInMs = 3600000;

}

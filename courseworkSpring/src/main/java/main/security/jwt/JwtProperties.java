package main.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  private String secretKey = "sdlkfjgsdhfgahsdfnlgsdkgfhowrthgsghlskdjfhg87345735";
  private long validityInMs = 3 * 3600 * 1000;

  public long getValidityInMs() {
    return validityInMs;
  }

  public void setValidityInMs(long validityInMs) {
    this.validityInMs = validityInMs;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
}

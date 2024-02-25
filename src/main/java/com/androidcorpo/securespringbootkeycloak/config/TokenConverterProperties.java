package com.androidcorpo.securespringbootkeycloak.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @author Severin Mbekou <mbekou99@gmail.com>
 */
@Setter
@Configuration
@ConfigurationProperties(prefix = "token.converter")
public class TokenConverterProperties {
  @Getter private String resourceId;
  private String principalAttribute;

  public Optional<String> getPrincipalAttribute() {
    return Optional.ofNullable(principalAttribute);
  }
}

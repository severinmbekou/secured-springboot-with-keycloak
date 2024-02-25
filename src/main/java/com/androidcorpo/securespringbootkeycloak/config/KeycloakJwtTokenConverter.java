package com.androidcorpo.securespringbootkeycloak.config;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * @author Severin Mbekou <mbekou99@gmail.com>
 */
@RequiredArgsConstructor
public class KeycloakJwtTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {
  private static final String RESOURCE_ACCESS = "";
  private static final String ROLES = "";
  private static final String ROLE_PREFIX = "ROLE_";
  private final TokenConverterProperties tokenConverterProperties;
  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

  @Override
  public JwtAuthenticationToken convert(Jwt jwt) {
    Stream<SimpleGrantedAuthority> accesses =
        Optional.of(jwt)
            .map(token -> token.getClaimAsMap(RESOURCE_ACCESS))
            .map(
                claimMap ->
                    (Map<String, Object>) claimMap.get(tokenConverterProperties.getResourceId()))
            .map(source -> source.get(ROLES))
            .stream()
            .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
            .distinct();

    Set<GrantedAuthority> authorities =
        Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), accesses)
            .collect(Collectors.toSet());

    String principalClaimName =
        tokenConverterProperties
            .getPrincipalAttribute()
            .map(jwt::getClaimAsString)
            .orElse(jwt.getClaimAsString(JwtClaimNames.SUB));

    return new JwtAuthenticationToken(jwt, authorities, principalClaimName);
  }
}

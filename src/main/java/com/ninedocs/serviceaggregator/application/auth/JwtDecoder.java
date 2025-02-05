package com.ninedocs.serviceaggregator.application.auth;

import com.ninedocs.serviceaggregator.application.auth.dto.JwtDecodeResult;
import com.ninedocs.serviceaggregator.application.auth.exception.TokenInvalidException;
import com.ninedocs.serviceaggregator.application.auth.exception.TokenInvalidException.InvalidCause;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtDecoder {

  private final Key secretKey;

  public JwtDecoder(@Value("${jwt.secret}") String secretKey) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public JwtDecodeResult decode(String token) {
    try {
      return baseDecode(token);

    } catch (ExpiredJwtException e) {
      throw new TokenInvalidException(InvalidCause.TOKEN_EXPIRED);

    } catch (Exception e) {
      log.debug("# Occurred Exception: {}", e.getClass().getName());
      log.debug("# Exception Message: {}", e.getMessage());

      throw new TokenInvalidException(InvalidCause.UNKNOWN);
    }
  }

  public JwtDecodeResult decodeWithoutException(String token) {
    try {
      return baseDecode(token);

    } catch (Exception e) {
      return JwtDecodeResult.builder()
          .userId(null)
          .build();
    }
  }

  private JwtDecodeResult baseDecode(String token) throws ExpiredJwtException {
    JwtParser jwtParser = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build();

    Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

    Claims claims = claimsJws.getBody();
    String subject = claims.getSubject();

    Long userId = Long.parseLong(subject);

    return JwtDecodeResult.builder()
        .userId(userId)
        .build();
  }
}

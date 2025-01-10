package com.ninedocs.serviceaggregator.application.auth;

import com.ninedocs.serviceaggregator.application.auth.dto.JwtDecodeResult;
import com.ninedocs.serviceaggregator.application.auth.dto.JwtDecodeResult.JwtInfo;
import io.jsonwebtoken.Claims;
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

  public JwtDecoder(
      @Value("${jwt.secret}") String secretKey
  ) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public JwtDecodeResult decode(String token) {
    try {
      JwtParser jwtParser = Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build();

      Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

      Claims claims = claimsJws.getBody();
      String subject = claims.getSubject();

      Long userId = Long.parseLong(subject);

      return JwtDecodeResult.builder()
          .isValid(true)
          .jwtInfo(JwtInfo.builder()
              .userId(userId)
              .build())
          .build();

    } catch (Exception e) {
      log.debug("# Occurred Exception: {}", e.getClass().getName());
      log.debug("# Exception Message: {}", e.getMessage());

      return JwtDecodeResult.builder()
          .isValid(false)
          .build();
    }
  }
}

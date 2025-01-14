package com.ninedocs.serviceaggregator.application.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ninedocs.serviceaggregator.application.auth.dto.JwtDecodeResult;
import com.ninedocs.serviceaggregator.application.auth.exception.TokenInvalidException;
import com.ninedocs.serviceaggregator.application.auth.exception.TokenInvalidException.InvalidCause;
import org.junit.jupiter.api.Test;

class JwtDecoderTest {

  private final JwtDecoder jwtDecoder = new JwtDecoder(
      "secret-64-byte-minimum-secret-key-which-must-be-long-enough-for-hmac"
  );

  @Test
  void decode() {
    /*
     * TEST_TOKEN
     * subject : 1
     * secret : 위 jwtDecoder 설정을 따름
     * 만료일자 : 2055년 1월 10일
     */
    String TEST_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzM2NDczMDQxLCJleHAiOjI2ODMyMDEwNDF9._230rMyINK2fLW8yuO6Nustma7eAqqA7rzjWjH5VM51s1Utlmx1OMgPWYlOwG4lhlEnQkMGUHQRYeo_3DWDM7w";
    JwtDecodeResult result = jwtDecoder.decode(TEST_TOKEN);

    assertEquals(result.getUserId(), 1L);
  }

  @Test
  void decode_whenTokenExpired() {
    /*
     * TEST_TOKEN
     * subject : 1
     * secret : 위 jwtDecoder 설정을 따름
     * 만료일시 : 2025년 1월 10일 오전 11시 28분
     */
    String TEST_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzM2NDc2MTMyLCJleHAiOjE3MzY0NzYxMzJ9.c00CZ10LomJ0zAVgTowdJTzRylZeUXG8bGugW6MrvuANOaeMbhzDxja8b9AVVDPn4mjQbJUgxSlYoROb9Uawvw";

    TokenInvalidException tokenInvalidException = assertThrows(
        TokenInvalidException.class,
        () -> jwtDecoder.decode(TEST_TOKEN)
    );
    assertEquals(tokenInvalidException.getErrorCode(), InvalidCause.TOKEN_EXPIRED.getDescription());
  }

  @Test
  void decode_whenTokenWeired() {
    /*
     * TEST_TOKEN
     * subject : 1
     * secret : 위 jwtDecoder 설정을 따름
     * 만료일시 : 2025년 1월 10일 오전 11시 28분
     */
    String TEST_TOKEN = "weired_token_weired_token_weired_token_weired_token_weired_token";

    TokenInvalidException tokenInvalidException = assertThrows(
        TokenInvalidException.class,
        () -> jwtDecoder.decode(TEST_TOKEN)
    );
    assertEquals(tokenInvalidException.getErrorCode(), InvalidCause.UNKNOWN.getDescription());
  }
}

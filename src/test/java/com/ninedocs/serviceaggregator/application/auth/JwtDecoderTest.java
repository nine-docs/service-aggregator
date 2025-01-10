package com.ninedocs.serviceaggregator.application.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ninedocs.serviceaggregator.application.auth.dto.JwtDecodeResult;
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

    assertEquals(result.getIsValid(), true);
    assertEquals(result.getJwtInfo().getUserId(), 1L);
  }
}

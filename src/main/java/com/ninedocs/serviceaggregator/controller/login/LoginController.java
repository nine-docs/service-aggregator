package com.ninedocs.serviceaggregator.controller.login;

import com.ninedocs.serviceaggregator.client.user.signin.SignInClient;
import com.ninedocs.serviceaggregator.client.user.signin.dto.SignInRequest;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.login.request.LoginRequest;
import com.ninedocs.serviceaggregator.controller.login.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "로그인")
@RestController
@RequiredArgsConstructor
public class LoginController {

  private final SignInClient signInClient;

  @Operation(summary = "로그인")
  @PostMapping("/api/v1/login")
  public Mono<ResponseEntity<ApiResponse<LoginResponse>>> login(
      @RequestBody @Valid LoginRequest loginRequest
  ) {
    return signInClient.signIn(
            SignInRequest.builder()
                .email(loginRequest.getEmail())
                .password(loginRequest.getPassword())
                .build()
        )
        .map(domainResponse -> ApiResponse.success(LoginResponse.builder()
            .accessToken(domainResponse.getToken())
            .accessTokenExpiredAt(domainResponse.getAccessTokenExpiredAt())
            .build()))
        .map(ResponseEntity::ok);
  }
}

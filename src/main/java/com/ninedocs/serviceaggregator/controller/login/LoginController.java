package com.ninedocs.serviceaggregator.controller.login;

import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.login.exception.LoginFailedException;
import com.ninedocs.serviceaggregator.controller.login.request.LoginRequest;
import com.ninedocs.serviceaggregator.controller.login.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "로그인")
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

  @Operation(summary = "로그인")
  @PostMapping
  public Mono<ResponseEntity<ApiResponse<LoginResponse>>> login(
      @RequestBody @Valid LoginRequest loginRequest
  ) {
    if (loginRequest.getEmail().equals("test@email.com")
        && loginRequest.getPassword().equals("test-password")) {
      return Mono.just(ResponseEntity.ok(ApiResponse.success(
          LoginResponse.builder()
              .accessToken("ejbqpio3wejdfqlkwaedsmvwejkflskedjf")
              .accessTokenExpiredAt(LocalDateTime.now().plusHours(6))
              .build()
      )));
    }
    throw new LoginFailedException();
  }
}

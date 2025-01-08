package com.ninedocs.serviceaggregator.controller.register.register;

import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.register.register.dto.RegisterRequest;
import com.ninedocs.serviceaggregator.controller.register.register.dto.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "회원가입")
@RequestMapping("/api/v1/register")
@RestController
public class RegisterController {

  @Operation(summary = "회원 가입 (구독) Mock")
  @PostMapping
  public Mono<ResponseEntity<ApiResponse<RegisterResponse>>> register(
      @RequestBody @Valid RegisterRequest registerRequest
  ) {
    return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
        RegisterResponse.builder()
            .userId(1L)
            .accessToken("ejb98owi12efjdsldblahblahblahbl3ahblahblahblahblah")
            .accessTokenExpiredAt(LocalDateTime.now().plusHours(6))
            .build()
    )));
  }
}

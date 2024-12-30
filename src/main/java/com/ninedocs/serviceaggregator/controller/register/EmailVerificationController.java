package com.ninedocs.serviceaggregator.controller.register;

import com.ninedocs.serviceaggregator.controller.common.response.HttpApiResponse;
import com.ninedocs.serviceaggregator.controller.register.request.VerificationCodeCreateRequest;
import com.ninedocs.serviceaggregator.controller.register.response.VerificationCodeCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "이메일 인증")
@RestController
public class EmailVerificationController {

  @Operation(summary = "이메일 인증 코드 발송")
  @PostMapping("/api/v1/register/email-verification-code")
  public Mono<HttpApiResponse<VerificationCodeCreateResponse>> sendEmailVerificationCode(
      @RequestBody @Valid VerificationCodeCreateRequest request
  ) {
    return Mono.just(HttpApiResponse.success(
        VerificationCodeCreateResponse.builder()
            .verificationCodeExpiredAt(LocalDateTime.now().plusMinutes(5))
            .build()
    ));
  }
}

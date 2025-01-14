package com.ninedocs.serviceaggregator.controller.register.emailverification;

import com.ninedocs.serviceaggregator.client.user.emailverification.EmailVerificationClient;
import com.ninedocs.serviceaggregator.client.user.emailverification.dto.EmailVerificationRequest;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.register.emailverification.dto.EmailVerifiedResponse;
import com.ninedocs.serviceaggregator.controller.register.emailverification.dto.EmailVerifyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "회원가입")
@RestController
@RequiredArgsConstructor
public class EmailVerificationController {

  private final EmailVerificationClient client;

  @Operation(summary = "이메일 인증")
  @PostMapping("/api/v1/register/email-verification")
  public Mono<ResponseEntity<ApiResponse<EmailVerifiedResponse>>> verifyEmail(
      @RequestBody @Valid EmailVerifyRequest request
  ) {
    return client.verifyEmail(
            EmailVerificationRequest.builder()
                .email(request.getEmail())
                .emailVerificationCode(request.getEmailVerificationCode())
                .build()
        )
        .map(domainResponse -> ApiResponse.success(EmailVerifiedResponse.builder()
            .verificationExpiredAt(domainResponse.getVerificationExpiredAt())
            .build()))
        .map(ResponseEntity::ok);
  }
}

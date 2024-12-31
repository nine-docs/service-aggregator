package com.ninedocs.serviceaggregator.controller.register;

import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.register.exception.EmailDuplicateException;
import com.ninedocs.serviceaggregator.controller.register.exception.EmailVerificationFailedException;
import com.ninedocs.serviceaggregator.controller.register.request.EmailVerifyRequest;
import com.ninedocs.serviceaggregator.controller.register.request.VerificationCodeCreateRequest;
import com.ninedocs.serviceaggregator.controller.register.response.EmailVerifiedResponse;
import com.ninedocs.serviceaggregator.controller.register.response.VerificationCodeCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "이메일 인증")
@RestController
@RequestMapping("/api/v1/register")
public class EmailVerificationController {

  @Operation(summary = "이메일 인증 코드 발송 Mock")
  @PostMapping("/email-verification-code")
  public Mono<ResponseEntity<ApiResponse<VerificationCodeCreateResponse>>> sendEmailVerificationCode(
      @RequestBody @Valid VerificationCodeCreateRequest request
  ) {
    if (request.getEmail().equals("duplicate@email.com")) {
      throw new EmailDuplicateException("EMAIL_DUPLICATED");
    }
    return Mono.just(ResponseEntity.ok(ApiResponse.success(
        VerificationCodeCreateResponse.builder()
            .verificationCodeExpiredAt(LocalDateTime.now().plusMinutes(5))
            .build()
    )));
  }

  @Operation(summary = "이메일 인증 Mock")
  @PostMapping("/email-verification")
  public Mono<ResponseEntity<ApiResponse<EmailVerifiedResponse>>> verifyEmail(
      @RequestBody @Valid EmailVerifyRequest request
  ) {
    if (request.getEmail().equals("fail@email.com")) {
      throw new EmailVerificationFailedException("VERIFICATION_FAILED");
    }
    return Mono.just(ResponseEntity.ok(ApiResponse.success(
        EmailVerifiedResponse.builder()
            .verificationExpiredAt(LocalDateTime.now().plusHours(1))
            .build()
    )));
  }
}

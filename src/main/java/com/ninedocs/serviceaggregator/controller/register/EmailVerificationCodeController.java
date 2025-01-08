package com.ninedocs.serviceaggregator.controller.register;

import com.ninedocs.serviceaggregator.client.user.emailverificationcode.EmailVerificationCodeClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.register.request.VerificationCodeCreateRequest;
import com.ninedocs.serviceaggregator.controller.register.response.VerificationCodeCreateResponse;
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
public class EmailVerificationCodeController {

  private final EmailVerificationCodeClient client;

  @Operation(summary = "이메일 인증 코드 발송")
  @PostMapping("/api/v1/register/email-verification-code")
  public Mono<ResponseEntity<ApiResponse<VerificationCodeCreateResponse>>> sendEmailVerificationCode(
      @RequestBody @Valid VerificationCodeCreateRequest request
  ) {
    return client.sendEmailVerificationCode(request.getEmail())
        .map(domainResponse -> ResponseEntity.ok(ApiResponse.success(
            VerificationCodeCreateResponse.builder()
                .verificationCodeExpiredAt(
                    domainResponse.getData().getVerificationCodeExpiredAt()
                )
                .build()
        )));
  }
}

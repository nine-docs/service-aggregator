package com.ninedocs.serviceaggregator.controller.mypage.profile.updatepassword;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.user.update.UpdatePasswordClient;
import com.ninedocs.serviceaggregator.client.user.update.dto.PasswordUpdateClientRequest;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.profile.updatepassword.dto.UpdatePasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "프로필")
@RestController
@RequiredArgsConstructor
public class UpdatePasswordController {

  private final JwtDecoder jwtDecoder;
  private final UpdatePasswordClient client;

  @Operation(summary = "비밀번호 변경")
  @PutMapping("/api/v1/my-page/profile/password")
  public Mono<ResponseEntity<ApiResponse<Void>>> updatePassword(
      @RequestHeader("Authentication") String authToken,
      @RequestBody @Valid UpdatePasswordRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return client.updatePassword(userId,
            PasswordUpdateClientRequest.builder()
                .originalPassword(request.getOriginalPassword())
                .newPassword(request.getNewPassword())
                .build()
        )
        .thenReturn(ResponseEntity.ok(ApiResponse.success()));
  }
}

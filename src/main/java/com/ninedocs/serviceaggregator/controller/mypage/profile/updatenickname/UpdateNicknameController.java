package com.ninedocs.serviceaggregator.controller.mypage.profile.updatenickname;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.user.update.UpdateNicknameClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.profile.common.dto.MyProfileResponse;
import com.ninedocs.serviceaggregator.controller.mypage.profile.updatenickname.dto.UpdateNicknameRequest;
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
public class UpdateNicknameController {

  private final JwtDecoder jwtDecoder;
  private final UpdateNicknameClient client;

  @Operation(summary = "닉네임 변경")
  @PutMapping("/api/v1/my-page/profile/nickname")
  public Mono<ResponseEntity<ApiResponse<MyProfileResponse>>> updateNickname(
      @RequestHeader("Authentication") String authToken,
      @RequestBody @Valid UpdateNicknameRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();
    return client.updateNickname(userId, request.getNewNickname())
        .map(user ->
            ResponseEntity.ok(ApiResponse.success(
                MyProfileResponse.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .build()
            ))
        );
  }
}

package com.ninedocs.serviceaggregator.controller.mypage.profile.query;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileQueryClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.profile.common.dto.MyProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "프로필")
@RestController
@RequiredArgsConstructor
public class ProfileQueryController {

  private final JwtDecoder jwtDecoder;
  private final UserProfileQueryClient client;

  @Operation(summary = "내 프로필 조회")
  @GetMapping("/api/v1/my-page/profile")
  public Mono<ResponseEntity<ApiResponse<MyProfileResponse>>> getMyProfile(
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return client.getUserProfile(userId).map(user ->
        ResponseEntity.ok(ApiResponse.success(
            MyProfileResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build()
        ))
    );
  }
}

package com.ninedocs.serviceaggregator.controller.mypage.subscription.scheduleupsert;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.article.userscheduleupsert.UserScheduleUpsertClient;
import com.ninedocs.serviceaggregator.client.article.userscheduleupsert.dto.UserScheduleUpsertRequest;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.scheduleupsert.dto.UpdateScheduleRequest;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.scheduleupsert.dto.UpdateScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "구독관리")
@RestController
@RequiredArgsConstructor
public class ScheduleUpsertController {

  private final JwtDecoder jwtDecoder;
  private final UserProfileClient userProfileClient;
  private final UserScheduleUpsertClient userScheduleUpsertClient;

  @Operation(summary = "내 메일 수신 주기 생성/수정")
  @PostMapping("/api/v1/my-page/subscription/schedule")
  public Mono<ResponseEntity<ApiResponse<UpdateScheduleResponse>>> createUpsertSchedule(
      @RequestHeader("Authentication") String authToken,
      @RequestBody @Valid UpdateScheduleRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return userProfileClient.userProfile(userId)
        .flatMap(userProfile -> userScheduleUpsertClient.upsertUserSchedule(
            UserScheduleUpsertRequest.builder()
                .userEmail(userProfile.getEmail())
                .schedules(request.getSchedules())
                .build()
        ))
        .map(responses -> ResponseEntity.ok(ApiResponse.success(
            UpdateScheduleResponse.of(responses)
        )));
  }
}

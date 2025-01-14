package com.ninedocs.serviceaggregator.controller.mypage.subscription.scheduleupsert;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.scheduleupsert.dto.UpdateScheduleRequest;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.scheduleupsert.dto.UpdateScheduleResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery.dto.SubscriptionResponse.DayOfWeek;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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

  @Operation(summary = "내 메일 수신 주기 생성/수정 Mock")
  @PostMapping("/api/v1/my-page/subscription/schedule")
  public Mono<ResponseEntity<ApiResponse<UpdateScheduleResponse>>> createUpsertSchedule(
      @RequestHeader("Authentication") String authToken,
      @RequestBody @Valid UpdateScheduleRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return Mono.just(ResponseEntity.ok(ApiResponse.success(
        UpdateScheduleResponse.of(List.of(DayOfWeek.MON, DayOfWeek.WED, DayOfWeek.SAT))
    )));
  }
}

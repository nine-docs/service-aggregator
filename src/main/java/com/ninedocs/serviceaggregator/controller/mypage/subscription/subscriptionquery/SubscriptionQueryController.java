package com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery.dto.SubscriptionResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery.dto.SubscriptionResponse.CategoryResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery.dto.SubscriptionResponse.DayOfWeek;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery.dto.SubscriptionResponse.MailReceivingScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "구독관리")
@RestController
@RequiredArgsConstructor
public class SubscriptionQueryController {

  private final JwtDecoder jwtDecoder;

  @Operation(summary = "내 구독 정보 조회")
  @GetMapping("/api/v1/my-page/subscription")
  public Mono<ResponseEntity<ApiResponse<SubscriptionResponse>>> getSubscription(
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return Mono.just(ResponseEntity.ok(ApiResponse.success(
        SubscriptionResponse.builder()
            .categories(List.of(
                CategoryResponse.builder()
                    .id(1L)
                    .name("Kubernetes")
                    .build(),
                CategoryResponse.builder()
                    .id(2L)
                    .name("Helm")
                    .build()
            ))
            .mailReceivingSchedule(MailReceivingScheduleResponse.builder()
                .dayOfWeeks(List.of(DayOfWeek.MON, DayOfWeek.WED, DayOfWeek.SAT))
                .build())
            .build()
    )));
  }
}

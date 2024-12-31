package com.ninedocs.serviceaggregator.controller.mypage;

import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.response.SubscriptionResponse;
import com.ninedocs.serviceaggregator.controller.mypage.response.SubscriptionResponse.CategoryResponse;
import com.ninedocs.serviceaggregator.controller.mypage.response.SubscriptionResponse.DayOfWeek;
import com.ninedocs.serviceaggregator.controller.mypage.response.SubscriptionResponse.MailReceivingScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "구독 관리")
@RestController
@RequestMapping("/api/v1/my-page/subscription")
public class SubscriptionController {

  @Operation(summary = "내 구독 정보 조회")
  @GetMapping
  public Mono<ResponseEntity<ApiResponse<SubscriptionResponse>>> getSubscription(
      @RequestHeader("Authentication") String authToken
  ) {
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

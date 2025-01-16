package com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.article.usercategoryquery.UserCategoryQueryClient;
import com.ninedocs.serviceaggregator.client.article.userschedulequery.UserScheduleQueryClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery.dto.SubscriptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
  private final UserCategoryQueryClient userCategoryQueryClient;
  private final UserScheduleQueryClient userScheduleQueryClient;

  @Operation(summary = "내 구독 정보 조회")
  @GetMapping("/api/v1/my-page/subscription")
  public Mono<ResponseEntity<ApiResponse<SubscriptionResponse>>> getSubscription(
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return Mono.zip(
            userCategoryQueryClient.getUserCategories(userId),
            userScheduleQueryClient.getUserSchedules(userId),
            SubscriptionResponse::from
        )
        .map(subscriptionResponse -> ResponseEntity.ok(ApiResponse.success(subscriptionResponse)));
  }
}

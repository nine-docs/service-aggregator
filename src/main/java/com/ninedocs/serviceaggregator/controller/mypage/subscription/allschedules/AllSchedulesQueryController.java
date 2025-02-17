package com.ninedocs.serviceaggregator.controller.mypage.subscription.allschedules;

import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.allschedules.dto.AllSchedulesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "구독관리")
@RestController
@RequiredArgsConstructor
public class AllSchedulesQueryController {

  @Operation(summary = "메일 수신 주기 전체 목록 조회")
  @GetMapping("/api/v1/my-page/subscription/available-schedules")
  public Mono<ResponseEntity<ApiResponse<AllSchedulesResponse>>> getAvailableSchedules() {
    return Mono.just(ResponseEntity.ok(ApiResponse.success(
        AllSchedulesResponse.builder()
            .schedules(Arrays.asList("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"))
            .build()
    )));
  }
}

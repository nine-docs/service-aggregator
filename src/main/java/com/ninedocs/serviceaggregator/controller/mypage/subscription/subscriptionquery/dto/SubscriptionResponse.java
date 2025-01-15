package com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery.dto;

import com.ninedocs.serviceaggregator.client.article.usercategoryquery.dto.UserCategoryQueryResponse;
import com.ninedocs.serviceaggregator.client.article.userschedulequery.dto.UserScheduleQueryResponse;
import com.ninedocs.serviceaggregator.client.common.dto.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SubscriptionResponse {

  private List<CategoryResponse> categories;
  private MailReceivingScheduleResponse mailReceivingSchedule;

  public static SubscriptionResponse from(
      UserCategoryQueryResponse userCategoryQueryResponse,
      UserScheduleQueryResponse userScheduleQueryResponse
  ) {
    return SubscriptionResponse.builder()
        .categories(
            userCategoryQueryResponse.getCategoryTitles().stream()
                .map(userCategory -> CategoryResponse.builder()
                    .name(userCategory)
                    .build())
                .collect(Collectors.toList())
        )
        .mailReceivingSchedule(
            MailReceivingScheduleResponse.builder()
                .dayOfWeeks(
                    userScheduleQueryResponse.getSchedules().stream()
                        .map(DayOfWeek::valueOf)
                        .collect(Collectors.toList())
                )
                .build()
        )
        .build();
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class CategoryResponse {

    private String name;
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class MailReceivingScheduleResponse {

    private List<DayOfWeek> dayOfWeeks;
  }
}

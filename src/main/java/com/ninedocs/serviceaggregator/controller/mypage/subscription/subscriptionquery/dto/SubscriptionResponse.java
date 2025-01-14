package com.ninedocs.serviceaggregator.controller.mypage.subscription.subscriptionquery.dto;

import com.ninedocs.serviceaggregator.client.common.dto.DayOfWeek;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubscriptionResponse {

  private List<CategoryResponse> categories;
  private MailReceivingScheduleResponse mailReceivingSchedule;

  @Getter
  @Builder
  public static class CategoryResponse {

    private Long id;
    private String name;
  }

  @Getter
  @Builder
  public static class MailReceivingScheduleResponse {

    private List<DayOfWeek> dayOfWeeks;
  }
}

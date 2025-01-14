package com.ninedocs.serviceaggregator.controller.mypage.subscription.scheduleupsert.dto;

import com.ninedocs.serviceaggregator.client.common.dto.DayOfWeek;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UpdateScheduleResponse {

  private MailReceivingScheduleResponse mailReceivingSchedule;

  public static UpdateScheduleResponse of(List<DayOfWeek> dayOfWeeks) {
    return UpdateScheduleResponse.builder()
        .mailReceivingSchedule(MailReceivingScheduleResponse.builder()
            .dayOfWeeks(dayOfWeeks)
            .build())
        .build();
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class MailReceivingScheduleResponse {

    private List<DayOfWeek> dayOfWeeks;
  }
}

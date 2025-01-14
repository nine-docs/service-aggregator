package com.ninedocs.serviceaggregator.controller.mypage.subscription.scheduleupsert.dto;

import com.ninedocs.serviceaggregator.client.common.dto.DayOfWeek;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

  @Size(max = 7)
  private List<DayOfWeek> schedules;
}

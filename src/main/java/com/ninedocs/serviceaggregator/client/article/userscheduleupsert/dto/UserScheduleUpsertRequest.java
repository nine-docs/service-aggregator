package com.ninedocs.serviceaggregator.client.article.userscheduleupsert.dto;

import com.ninedocs.serviceaggregator.client.common.dto.DayOfWeek;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserScheduleUpsertRequest {

  private String userEmail;
  private List<DayOfWeek> schedules;
}

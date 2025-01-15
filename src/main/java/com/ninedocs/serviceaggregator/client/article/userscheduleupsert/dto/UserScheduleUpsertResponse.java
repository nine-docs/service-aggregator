package com.ninedocs.serviceaggregator.client.article.userscheduleupsert.dto;

import com.ninedocs.serviceaggregator.client.common.dto.DayOfWeek;
import lombok.Getter;

@Getter
public class UserScheduleUpsertResponse {

  private Long id;
  private Long userId;
  private String userEmail;
  private DayOfWeek dayOfWeek;
}

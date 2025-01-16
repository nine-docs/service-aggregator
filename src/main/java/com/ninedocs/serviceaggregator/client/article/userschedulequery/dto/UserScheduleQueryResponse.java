package com.ninedocs.serviceaggregator.client.article.userschedulequery.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class UserScheduleQueryResponse {

  private Long userId;
  private List<String> schedules;
}

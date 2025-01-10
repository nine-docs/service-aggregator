package com.ninedocs.serviceaggregator.controller.mypage.subscription.allschedules.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllSchedulesResponse {

  private List<String> schedules;
}

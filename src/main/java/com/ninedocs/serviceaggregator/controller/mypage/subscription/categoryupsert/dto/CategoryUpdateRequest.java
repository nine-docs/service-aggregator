package com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert.dto;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class CategoryUpdateRequest {

  @Size(max = 100)
  private List<Long> categoryIds;
}

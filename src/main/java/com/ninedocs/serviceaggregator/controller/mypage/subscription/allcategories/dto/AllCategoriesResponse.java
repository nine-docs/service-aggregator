package com.ninedocs.serviceaggregator.controller.mypage.subscription.allcategories.dto;

import com.ninedocs.serviceaggregator.client.article.allcategories.dto.CategoryResult;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllCategoriesResponse {

  private List<CategoryResponse> categories;

  public static AllCategoriesResponse of(List<CategoryResult> categoryResults) {
    return AllCategoriesResponse.builder()
        .categories(
            categoryResults.stream()
                .map(categoryResult -> CategoryResponse.builder()
                    .id(categoryResult.getId())
                    .name(categoryResult.getTitle())
                    .build())
                .toList()
        )
        .build();
  }

  @Getter
  @Builder
  public static class CategoryResponse {

    private Long id;
    private String name;
  }
}

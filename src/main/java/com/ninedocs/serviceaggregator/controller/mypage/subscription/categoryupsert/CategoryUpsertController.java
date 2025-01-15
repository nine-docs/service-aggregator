package com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.article.usercategoryupsert.UserCategoryUpsertClient;
import com.ninedocs.serviceaggregator.client.article.usercategoryupsert.dto.UserCategoryUpsertRequest;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert.dto.CategoryUpdateRequest;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert.dto.CategoryUpdateResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert.dto.CategoryUpdateResponse.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "구독관리")
@RestController
@RequiredArgsConstructor
public class CategoryUpsertController {

  private final JwtDecoder jwtDecoder;
  private final UserProfileClient userProfileClient;
  private final UserCategoryUpsertClient userCategoryUpsertClient;

  @Operation(summary = "내 구독 카테고리 생성/수정")
  @PostMapping("/api/v1/my-page/subscription/my-categories")
  public Mono<ResponseEntity<ApiResponse<CategoryUpdateResponse>>> createUpsertMyCategories(
      @RequestHeader("Authentication") String authToken,
      @RequestBody @Valid CategoryUpdateRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return userProfileClient.userProfile(userId)
        .flatMap(userProfile -> userCategoryUpsertClient.upsertUserCategory(
            UserCategoryUpsertRequest.builder()
                .userId(userId)
                .userEmail(userProfile.getEmail())
                .categoryIds(request.getCategoryIds())
                .build()
        ))
        .map(results -> ResponseEntity.ok(ApiResponse.success(
            CategoryUpdateResponse.builder()
                .categories(
                    results.stream()
                        .map(userCategory -> CategoryResponse.builder()
                            .id(userCategory.getCategoryId())
                            .name(userCategory.getCategoryTitle())
                            .build())
                        .toList()
                )
                .build()
        )));
  }
}

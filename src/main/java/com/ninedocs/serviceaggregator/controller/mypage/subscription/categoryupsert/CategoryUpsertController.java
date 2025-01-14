package com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.article.usercategoryupsert.UserCategoryUpsertClient;
import com.ninedocs.serviceaggregator.client.user.profile.UserProfileClient;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert.dto.CategoryUpdateRequest;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert.dto.CategoryUpdateResponse;
import com.ninedocs.serviceaggregator.controller.mypage.subscription.categoryupsert.dto.CategoryUpdateResponse.CategoryResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CategoryUpsertController {

  private final JwtDecoder jwtDecoder;
  private final UserProfileClient userProfileClient;
  private final UserCategoryUpsertClient userCategoryUpsertClient;

  @PostMapping("/api/v1/my-page/subscription/my-categories")
  public Mono<ResponseEntity<ApiResponse<CategoryUpdateResponse>>> createUpsertMyCategories(
      @RequestHeader("Authentication") String authToken,
      @RequestBody @Valid CategoryUpdateRequest request
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return userProfileClient.userProfile(userId)
        .flatMap(result -> Mono.just(ResponseEntity.ok(ApiResponse.success(
            CategoryUpdateResponse.builder()
                .categories(List.of(CategoryResponse.builder()
                    .id(1L)
                    .name("Kubernetes")
                    .build()))
                .build()
        ))));
  }
}

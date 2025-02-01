package com.ninedocs.serviceaggregator.controller.bookmarkcommand.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookmarkCreateRequest {

    @Schema(example = "1")
    @NotNull
    private Long articleId;
}

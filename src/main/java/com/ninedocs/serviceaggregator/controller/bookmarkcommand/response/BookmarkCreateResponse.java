package com.ninedocs.serviceaggregator.controller.bookmarkcommand.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkCreateResponse {

    private Long bookmarkId;
    private Long articleId;
}

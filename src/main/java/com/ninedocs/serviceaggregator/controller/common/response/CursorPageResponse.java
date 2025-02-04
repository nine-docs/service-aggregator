package com.ninedocs.serviceaggregator.controller.common.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CursorPageResponse<ItemType, CursorType> {

  private CursorType cursor;
  private List<ItemType> items;

  public static <ItemType, CursorType> CursorPageResponse<ItemType, CursorType> of(
      List<ItemType> items, CursorType cursor
  ) {
    return CursorPageResponse.<ItemType, CursorType>builder()
        .cursor(cursor)
        .items(items)
        .build();
  }
}

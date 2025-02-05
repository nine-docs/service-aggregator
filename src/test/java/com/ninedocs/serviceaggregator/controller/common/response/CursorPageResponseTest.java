package com.ninedocs.serviceaggregator.controller.common.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class CursorPageResponseTest {

  @Test
  void of() {
    List<String> testItem = List.of("item1", "item2", "item3");
    CursorPageResponse<String, Long> testResult = CursorPageResponse.of(testItem, 3L);
    assertEquals(3L, testResult.getCursor());
    assertEquals("item1", testResult.getItems().get(0));
  }
}

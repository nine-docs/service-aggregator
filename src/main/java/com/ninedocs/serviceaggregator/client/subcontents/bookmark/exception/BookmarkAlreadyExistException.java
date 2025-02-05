package com.ninedocs.serviceaggregator.client.subcontents.bookmark.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class BookmarkAlreadyExistException extends CustomException {

  @Override
  public String getErrorCode() {
    return "ALREADY_BOOKMARKED";
  }
}

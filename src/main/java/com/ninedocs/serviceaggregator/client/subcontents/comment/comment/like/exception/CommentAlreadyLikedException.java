package com.ninedocs.serviceaggregator.client.subcontents.comment.comment.like.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class CommentAlreadyLikedException extends CustomException {

  @Override
  public String getErrorCode() {
    return "COMMENT_ALREADY_LIKED";
  }
}

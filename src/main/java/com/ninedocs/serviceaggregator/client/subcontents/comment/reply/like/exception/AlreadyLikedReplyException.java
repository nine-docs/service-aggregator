package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class AlreadyLikedReplyException extends CustomException {

  @Override
  public String getErrorCode() {
    return "ALREADY_LIKED_REPLY";
  }
}

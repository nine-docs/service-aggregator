package com.ninedocs.serviceaggregator.client.subcontents.comment.reply.like.exception;

import com.ninedocs.serviceaggregator.controller.common.exception.CustomException;

public class ReplyNotLikedException extends CustomException {

  @Override
  public String getErrorCode() {
    return "REPLY_NOT_LIKED";
  }
}

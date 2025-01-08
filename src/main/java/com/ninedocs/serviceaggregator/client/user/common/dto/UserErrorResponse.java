package com.ninedocs.serviceaggregator.client.user.common.dto;

import lombok.Getter;

@Getter
public class UserErrorResponse {

  private String timestamp;
  private int status;
  private String error;
  private String message;
  private String trace;
  private String path;
}

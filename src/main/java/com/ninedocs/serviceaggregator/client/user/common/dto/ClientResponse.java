package com.ninedocs.serviceaggregator.client.user.common.dto;

import lombok.Getter;

@Getter
public class ClientResponse<T> {

  private Boolean success;
  private String errorCode;
  private T data;
}

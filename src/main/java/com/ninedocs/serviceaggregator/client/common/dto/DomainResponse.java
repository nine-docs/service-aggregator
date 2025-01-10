package com.ninedocs.serviceaggregator.client.common.dto;

import lombok.Getter;

@Getter
public class DomainResponse<T> {

  private Boolean success;
  private String errorCode;
  private T data;
}

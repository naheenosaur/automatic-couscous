package com.naheenosaur.employee.dto;

import lombok.Getter;

@Getter
public class FileNotSupportedErrorResponse {
  private final String message;

  public FileNotSupportedErrorResponse(final String message) {
    this.message = message;
  }
}

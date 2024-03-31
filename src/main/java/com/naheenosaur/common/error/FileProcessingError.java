package com.naheenosaur.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileProcessingError extends BaseError {
  private final String message;
}

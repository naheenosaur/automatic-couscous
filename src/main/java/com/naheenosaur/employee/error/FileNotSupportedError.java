package com.naheenosaur.employee.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileNotSupportedError extends EmployeeServiceError {
  private final String message;
}

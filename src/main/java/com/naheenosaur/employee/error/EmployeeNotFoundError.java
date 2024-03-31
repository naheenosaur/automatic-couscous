package com.naheenosaur.employee.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmployeeNotFoundError extends EmployeeServiceError {
  private final String name;
}

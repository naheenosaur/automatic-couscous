package com.naheenosaur.employee.dto;

import lombok.Getter;

@Getter
public class EmployeeNotFoundErrorResponse {
  private final String employeeName;

  public EmployeeNotFoundErrorResponse(final String name) {
    this.employeeName = name;
  }
}

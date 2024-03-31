package com.naheenosaur.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record EmployeeGetResponse(
    String name,
    String email,
    String tel,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate joined
) {
}

package com.naheenosaur.employee;

import com.naheenosaur.common.error.FileProcessingError;
import com.naheenosaur.employee.dto.EmployeeGetResponse;
import com.naheenosaur.employee.error.FileNotSupportedError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
  private final EmployeeService employeeService;
  private final EmployeeParseService employeeParseService;

  @GetMapping(path = "/api/employee")
  ResponseEntity<List<EmployeeGetResponse>> getEmployee(
      @RequestParam(name = "page") final int page,
      @RequestParam(name = "pageSize") final int pageSize
  ) {
    final List<Employee> employees = this.employeeService.getEmployees(page, pageSize);
    return ResponseEntity.ok(employees.stream().map(employee -> new EmployeeGetResponse(
        employee.getName(),
        employee.getEmail(),
        employee.getPhone(),
        employee.getJoined()
    )).toList());
  }

  @GetMapping(path = "/api/employee/{name}")
  ResponseEntity<EmployeeGetResponse> getEmployee(@PathVariable final String name) {
    final Employee employee = this.employeeService.getEmployee(name);
    return ResponseEntity.ok(
        new EmployeeGetResponse(
            employee.getName(),
            employee.getEmail(),
            employee.getPhone(),
            employee.getJoined()));
  }

  @PostMapping(
      path = "/api/employee",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
  )
  public ResponseEntity<String> uploadEmployee(
      @RequestPart(value = "file", required = false) final MultipartFile file
  ) {
    if (file == null) {
      throw new FileNotSupportedError("File is required");
    }

    if (file.isEmpty() || file.getContentType() == null) {
      throw new FileNotSupportedError("File is empty or unsupported");
    }

    List<Employee> employees = new ArrayList<>();
    try {
      if (file.getContentType().equals("text/csv")) {
        employees.addAll(employeeParseService.getFromCsv(file));
      } else if (file.getContentType().equals("application/json")) {
        employees.addAll(employeeParseService.getFromJson(file, Employee.class));
      } else {
        throw new FileNotSupportedError("Unsupported file format");
      }
    } catch (IOException e) {
      throw new FileProcessingError( "Error processing file");
    }
    int successSize = employeeService.createEmployee(employees);
    return ResponseEntity.status(HttpStatus.CREATED).body("%s employee(s) created".formatted(successSize));
  }

  @PostMapping(
      path = "/api/employee",
      consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  public ResponseEntity<String> uploadEmployee(
      @RequestBody(required = false) List<Employee> employeesRequest
  ) {
    if (employeesRequest == null) {
      return ResponseEntity.badRequest().body("Data is required");
    }
    int successSize = employeeService.createEmployee(employeesRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body("%s employee(s) created".formatted(successSize));
  }

  @PostMapping(path = "/api/employee", consumes = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> uploadEmployee(@RequestBody String employeesRequest) throws IOException {
    if (employeesRequest == null) {
      return ResponseEntity.badRequest().body("Data is required");
    }
    List<Employee> employees = employeeParseService.getFromCsv(employeesRequest);
    int successSize = employeeService.createEmployee(employees);
    return ResponseEntity.status(HttpStatus.CREATED).body("%s employee(s) created".formatted(successSize));
  }


}

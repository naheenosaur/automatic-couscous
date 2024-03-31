package com.naheenosaur.employee;

import com.naheenosaur.employee.error.EmployeeNotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Transactional
  @NonNull
  public int createEmployee(List<Employee> employee) {
    return this.employeeRepository.saveAll(employee).size();
  }


  @Transactional(readOnly = true)
  @NonNull
  public Employee getEmployee(String name) {
    final Optional<Employee> employee = this.employeeRepository.findByName(name);
    if (employee.isEmpty()) {
      throw new EmployeeNotFoundError(name);
    }
    return employee.get();
  }

  public List<Employee> getEmployees(int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return employeeRepository.findAll(pageable).getContent();
  }

}

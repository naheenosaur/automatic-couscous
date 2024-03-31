package com.naheenosaur.employee;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeParseService {
  public List<Employee> getFromCsv(String employeesRequest) throws IOException {
    List<Employee> employees = new ArrayList<>();
    BufferedReader br = new BufferedReader(new StringReader(employeesRequest));
    String line;
    while ((line = br.readLine()) != null) {
      String[] data = line.split(",");
      if (data.length == 4) {
        String name = data[0].trim();
        String email = data[1].trim();
        String phone = data[2].trim();
        LocalDate joined = LocalDate.parse(data[3].trim());
        employees.add(
            Employee.builder().name(name).email(email).phone(phone).joined(joined).build()
        );
      }
    }
    return employees;
  }

  public List<Employee> getFromCsv(MultipartFile file) throws IOException {
    List<Employee> employees = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
    String line;
    while ((line = br.readLine()) != null) {
      String[] data = line.split(",");
      employees.add(Employee.from(data));
    }
    return employees;
  }

  @NonNull
  public <T> List<T> getFromJson(final MultipartFile file, final Class<T> clazz) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    JavaType listType = typeFactory.constructCollectionType(List.class, clazz);
    return objectMapper.readValue(file.getBytes(), listType);
  }

}

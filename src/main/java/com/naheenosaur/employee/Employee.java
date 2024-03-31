package com.naheenosaur.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private String name;
  @NonNull
  private String email;
  @JsonProperty("tel")
  @NonNull
  private String phone;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @NonNull
  private LocalDate joined;

  public static Employee from(String[] data) {
    if (data.length != 4) {
      throw new IllegalArgumentException("Invalid data");
    }
    String name = data[0].trim();
    String email = data[1].trim();
    String phone = data[2].trim().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
    DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    LocalDate joined = LocalDate.parse(data[3].trim(), formatterInput);
    return Employee.builder().name(name).email(email).phone(phone).joined(joined).build();
  }
}

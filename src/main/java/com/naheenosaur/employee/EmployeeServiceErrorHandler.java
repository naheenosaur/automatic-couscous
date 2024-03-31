package com.naheenosaur.employee;

import com.naheenosaur.employee.dto.EmployeeNotFoundErrorResponse;
import com.naheenosaur.employee.dto.FileNotSupportedErrorResponse;
import com.naheenosaur.employee.error.EmployeeNotFoundError;
import com.naheenosaur.employee.error.FileNotSupportedError;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class EmployeeServiceErrorHandler {
    @ExceptionHandler(FileNotSupportedError.class)
    public ResponseEntity<FileNotSupportedErrorResponse> handleFileNotSupportedError(
        FileNotSupportedError fileNotSupportedError) {
        log.info("FileNotSupportedError happened.", fileNotSupportedError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new FileNotSupportedErrorResponse(fileNotSupportedError.getMessage()));
    }

    @ExceptionHandler(EmployeeNotFoundError.class)
    public ResponseEntity<EmployeeNotFoundErrorResponse> handleEmployeeNotFoundError(
        EmployeeNotFoundError employeeNotFoundError) {
        log.info("EmployeeNotFoundError happened.", employeeNotFoundError);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new EmployeeNotFoundErrorResponse(employeeNotFoundError.getName()));
    }
}

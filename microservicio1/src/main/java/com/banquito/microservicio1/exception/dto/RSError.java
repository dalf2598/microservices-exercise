package com.banquito.microservicio1.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RSError {
    private String status;
    private String description;
}

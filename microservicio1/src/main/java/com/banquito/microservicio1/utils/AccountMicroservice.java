package com.banquito.microservicio1.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "spring.variable.account")
@Configuration("accountProperties")
public class AccountMicroservice {
    private String value;
}

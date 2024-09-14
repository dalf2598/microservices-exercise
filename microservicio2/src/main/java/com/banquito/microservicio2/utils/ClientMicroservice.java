package com.banquito.microservicio2.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "spring.variable.client")
@Configuration("clientProperties")
public class ClientMicroservice {
    private String value;
}

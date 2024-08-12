package com.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.ecom.config.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileUploadProperties.class)
public class TechGridApplication {
    public static void main(String[] args) {
        SpringApplication.run(TechGridApplication.class, args);
    }
}

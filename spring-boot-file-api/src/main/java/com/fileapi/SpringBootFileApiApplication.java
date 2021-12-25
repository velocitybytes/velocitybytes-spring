package com.fileapi;

import com.fileapi.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class SpringBootFileApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFileApiApplication.class, args);
    }

}

package com.notebytes;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@Log4j2
public class NotebytesApplication {

    @PostConstruct
    void init() {
        log.debug("Setting timezone to UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(NotebytesApplication.class, args);
    }

}

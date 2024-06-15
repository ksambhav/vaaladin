package com.samsoft;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GreetService {

    public String greet(String name) {
        log.info("Greeting {}", name);
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }

}

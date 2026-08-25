package com.sweflow.docagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.sweflow.docagent",
                "com.sweflow.storage"
        }
)
public class DocAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocAgentApplication.class, args);
    }

}

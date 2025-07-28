package org.example.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.example.hw", "org.example.synth.core"})
public class BishopMain {

    public static void main(String[] args) {
        SpringApplication.run(BishopMain.class, args);
    }

}
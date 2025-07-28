package org.example.synth.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "org.example.synth.core")
public class SyntheticHumanCoreAutoConfiguration {
}
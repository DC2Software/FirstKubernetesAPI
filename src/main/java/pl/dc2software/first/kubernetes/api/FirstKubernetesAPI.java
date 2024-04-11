package pl.dc2software.first.kubernetes.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FirstKubernetesAPI {

    public static void main(String[] args) {
        SpringApplication.run(FirstKubernetesAPI.class, args);
    }
}

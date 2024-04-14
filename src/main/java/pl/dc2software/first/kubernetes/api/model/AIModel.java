package pl.dc2software.first.kubernetes.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AIModel {
    GPT3_5("gpt-3.5-turbo"),
    GPT4("gpt-4");

    private String model;
}

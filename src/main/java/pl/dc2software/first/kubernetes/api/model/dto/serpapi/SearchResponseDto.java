package pl.dc2software.first.kubernetes.api.model.dto.serpapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SearchResponseDto(@JsonProperty("organic_results") List<Result> organicResults) {

    public record Result(int position, String title, String link, String source) {}
}

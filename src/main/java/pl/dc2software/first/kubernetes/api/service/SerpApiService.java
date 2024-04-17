package pl.dc2software.first.kubernetes.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.dc2software.first.kubernetes.api.model.dto.serpapi.SearchResponseDto;

import static pl.dc2software.first.kubernetes.api.Constants.SERP_API_SEARCH_URL;

@Slf4j
@Service
public class SerpApiService {

    @Value("${serp-api.api-key}")
    private String apiKey;

    public SearchResponseDto searchInGoogle(String query, String country) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = UriComponentsBuilder.fromHttpUrl(SERP_API_SEARCH_URL)
                .queryParam("q", query)
                .queryParam("gl", country)
                .queryParam("api_key", apiKey)
                .queryParam("engine", "google")
                .encode()
                .toUriString();

        ResponseEntity<SearchResponseDto> response = restTemplate.getForEntity(uri, SearchResponseDto.class);

        return response.getBody();
    }
}

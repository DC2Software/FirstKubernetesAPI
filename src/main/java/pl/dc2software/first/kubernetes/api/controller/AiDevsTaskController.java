package pl.dc2software.first.kubernetes.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dc2software.first.kubernetes.api.model.AiDevsTaskRequestDto;
import pl.dc2software.first.kubernetes.api.model.AiDevsTaskResponseDto;
import pl.dc2software.first.kubernetes.api.service.OpenAiCompletionsService;

@RestController
@RequestMapping("/v1/ai-devs")
@AllArgsConstructor
public class AiDevsTaskController {

    private final OpenAiCompletionsService completionsService;

    @PostMapping
    public AiDevsTaskResponseDto answerQuestion(@RequestBody AiDevsTaskRequestDto requestContent) {
        String modelResponse = completionsService.respondWithGpt4(requestContent.question());
        return new AiDevsTaskResponseDto(modelResponse);
    }
}

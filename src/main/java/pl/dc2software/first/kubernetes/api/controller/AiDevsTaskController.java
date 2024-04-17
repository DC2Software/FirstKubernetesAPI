package pl.dc2software.first.kubernetes.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dc2software.first.kubernetes.api.model.AIModel;
import pl.dc2software.first.kubernetes.api.model.Category;
import pl.dc2software.first.kubernetes.api.model.dto.AiDevsTaskRequestDto;
import pl.dc2software.first.kubernetes.api.model.dto.AiDevsTaskResponseDto;
import pl.dc2software.first.kubernetes.api.model.dto.serpapi.SearchResponseDto;
import pl.dc2software.first.kubernetes.api.service.OpenAiCompletionsService;
import pl.dc2software.first.kubernetes.api.service.SerpApiService;
import pl.dc2software.first.kubernetes.api.service.UserInformationService;

@RestController
@RequestMapping("/v1/ai-devs")
@AllArgsConstructor
public class AiDevsTaskController {

    private final OpenAiCompletionsService completionsService;
    private final UserInformationService userInformationService;
    private final SerpApiService serpApiService;

    @PostMapping
    public AiDevsTaskResponseDto answerQuestionOrSaveInfo(@RequestBody AiDevsTaskRequestDto requestContent) {
        Category messageCategory = completionsService.categorizeWithGPT(requestContent.question(), AIModel.GPT3_5);
        if (messageCategory == Category.INFORMATION) {
            userInformationService.saveUserInformation(requestContent.question());
            return new AiDevsTaskResponseDto("Thanks for the information!");
        } else if (messageCategory == Category.URL) {
            SearchResponseDto response = serpApiService.searchInGoogle(requestContent.question(), "pl");
            return new AiDevsTaskResponseDto(response.organicResults().get(0).link());
        }
        String context = userInformationService.findUserInformation(requestContent.question());
        String modelResponse = completionsService.respondWithGpt(requestContent.question(), AIModel.GPT3_5, context);
        return new AiDevsTaskResponseDto(modelResponse);
    }
}

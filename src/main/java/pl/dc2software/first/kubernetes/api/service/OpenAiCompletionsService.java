package pl.dc2software.first.kubernetes.api.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OpenAiCompletionsService {

    private static final String ANSWER_QUESTION_SYSTEM_PROMPT = "Hello! I'm John. I answer questions as briefly as possible.";

    private OpenAiApi openAiApi;

    public String respondWithGpt4(String question) {
        log.info("Received question: {}", question);
        ResponseEntity<OpenAiApi.ChatCompletion> response = openAiApi.chatCompletionEntity(new OpenAiApi.ChatCompletionRequest(List.of(
                new OpenAiApi.ChatCompletionMessage(ANSWER_QUESTION_SYSTEM_PROMPT, OpenAiApi.ChatCompletionMessage.Role.SYSTEM),
                new OpenAiApi.ChatCompletionMessage(question, OpenAiApi.ChatCompletionMessage.Role.USER)),
                "gpt-4", 0.5F));
        if (response.getBody() == null) {
            throw new RuntimeException("Could not get model's response.");
        }
        String responseContent = response.getBody().choices().get(0).message().content();
        log.info("Returning model response: {}", responseContent);
        return responseContent;
    }
}

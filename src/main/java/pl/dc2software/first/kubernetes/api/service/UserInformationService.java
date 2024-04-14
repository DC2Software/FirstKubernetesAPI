package pl.dc2software.first.kubernetes.api.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserInformationService {

    private final VectorStore vectorStore;

    public void saveUserInformation(String information) {
        vectorStore.add(List.of(new Document(information)));
        log.info("Saved information: {}", information);
    }

    public String findUserInformation(String question) {
        List<Document> foundDocs = vectorStore.similaritySearch(SearchRequest.query(question).withTopK(1));
        Document relevantDocument = foundDocs.stream()
                .findFirst()
                .orElse(null);
        return relevantDocument == null ? "" : relevantDocument.getContent();
    }
}

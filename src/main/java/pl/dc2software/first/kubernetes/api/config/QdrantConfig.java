package pl.dc2software.first.kubernetes.api.config;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiEmbeddingClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.ExecutionException;

@Configuration
@Slf4j
public class QdrantConfig {

    @Bean
    @Primary
    public VectorStore userInformationVectorStore(OpenAiApi openAiApi) throws ExecutionException, InterruptedException {
        QdrantGrpcClient.Builder grpcClientBuilder = QdrantGrpcClient.newBuilder("localhost", 6334, false);
        QdrantClient client = new QdrantClient(grpcClientBuilder.build());
        client.deleteCollectionAsync("user-information").get();
        client.createCollectionAsync(
            Collections.CreateCollection.newBuilder()
                .setCollectionName("user-information")
                .setVectorsConfig(
                    Collections.VectorsConfig.newBuilder()
                        .setParams(
                            Collections.VectorParams.newBuilder()
                                .setSize(1536)
                                .setDistance(Collections.Distance.Cosine)
                                .build())
                        .build())
                .setQuantizationConfig(
                    Collections.QuantizationConfig.newBuilder()
                        .setBinary(Collections.BinaryQuantization.newBuilder().setAlwaysRam(true).build())
                        .build())
                .build())
        .get();

        return new QdrantVectorStore(client, "user-information", new OpenAiEmbeddingClient(openAiApi));
    }
}

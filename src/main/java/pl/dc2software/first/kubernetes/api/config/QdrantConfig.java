package pl.dc2software.first.kubernetes.api.config;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiEmbeddingClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.dc2software.first.kubernetes.api.model.dto.QdrantConfigDto;

import java.util.concurrent.ExecutionException;


@Configuration
@Slf4j
public class QdrantConfig {


    @Bean
    @ConfigurationProperties(prefix = "vectorstore.qdrant")
    public QdrantConfigDto qdrantConfigData() {
        return new QdrantConfigDto();
    }


    @Bean
    @Primary
    public QdrantVectorStore userInformationVectorStore(OpenAiApi openAiApi, QdrantConfigDto qdrantConfigData) throws ExecutionException, InterruptedException {
        QdrantGrpcClient.Builder grpcClientBuilder = QdrantGrpcClient.newBuilder(qdrantConfigData.getHost(), qdrantConfigData.getPort(), false);
        QdrantClient client = new QdrantClient(grpcClientBuilder.build());
        client.deleteCollectionAsync(qdrantConfigData.getCollectionName()).get();
        client.createCollectionAsync(
                        Collections.CreateCollection.newBuilder()
                                .setCollectionName(qdrantConfigData.getCollectionName())
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

        return new QdrantVectorStore(client, qdrantConfigData.getCollectionName(), new OpenAiEmbeddingClient(openAiApi));
    }
}


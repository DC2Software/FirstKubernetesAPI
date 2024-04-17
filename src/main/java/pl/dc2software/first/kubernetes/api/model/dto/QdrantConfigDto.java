package pl.dc2software.first.kubernetes.api.model.dto;

import lombok.Data;

@Data
public class QdrantConfigDto {
    private String host;
    private Integer port;
    private String collectionName;
}

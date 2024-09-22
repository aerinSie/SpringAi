package com.example.spring_ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.postgresml.PostgresMlEmbeddingModel;
import org.springframework.ai.postgresml.PostgresMlEmbeddingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class AiConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }


    @Bean
    public EmbeddingModel embeddingModel(JdbcTemplate jdbcTemplate) {
        return new PostgresMlEmbeddingModel(jdbcTemplate,
                PostgresMlEmbeddingOptions.builder()
//                        .withTransformer("DMetaSoul/sbert-chinese-general-v2")
//                        .withTransformer("distilbert-base-uncased")
//                        .withKwargs()
                        .withMetadataMode(MetadataMode.EMBED)
                        .withVectorType(PostgresMlEmbeddingModel.VectorType.PG_ARRAY)
            .build());
    }
}

package com.example.spring_ai.service;

import lombok.Data;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Service
public class SearchService {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final OllamaChatModel ollamaChatModel;
    private ChatMemory chatMemory = new InMemoryChatMemory();

    public String ragSearch(String query) throws IOException {


        return ChatClient.create(ollamaChatModel).prompt()
                .advisors(new MessageChatMemoryAdvisor(chatMemory, "1", 30)
                        , new QuestionAnswerAdvisor(vectorStore) //RAG增強器
                        , new SimpleLoggerAdvisor())
                .user(query)
                .call()
                .content();

    }

    public Resource search(String query) throws IOException {
        Path pathResult = Paths.get("./result.md");
        String result = chatClient.prompt().advisors(
                new QuestionAnswerAdvisor(vectorStore) //RAG增強器
                , new SimpleLoggerAdvisor()
        ).user(query).call().content();
        Files.writeString(pathResult, result);
        return new UrlResource(pathResult.toUri());
    }
}

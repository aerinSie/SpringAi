package com.example.spring_ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TxtService {
    @Value("classpath:./txt/springai.txt")
    private Resource textResource;

    private final VectorStore vectorStore;


    public List<Document> loadTextAsDocuments() {
        TextReader textReader = new TextReader(textResource);
        textReader.getCustomMetadata().put("ITHOME", "16th");
        return textReader.get();
    }


    public void importText() {
        TokenTextSplitter splitter = new TokenTextSplitter();
        vectorStore.write(splitter.split(loadTextAsDocuments()));
    }

}

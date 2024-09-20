package com.example.spring_ai.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.ai.transformer.SummaryMetadataEnricher;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfService {
    private final ChatModel chatModel;

    private final VectorStore vectorStore;

    public List<Document> loadPdfAsDocuments() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = new Resource[0];
        resources = resolver.getResources("./pdf/*.pdf");
        List<Document> allDocs = new ArrayList<>();

        for (Resource pdfResource : resources) {
            log.info("Process File:{}", pdfResource.getFilename());
            List<Document> docs = new ArrayList<>();
            List<Document> docs2 = new ArrayList<>();

            try {
                ParagraphPdfDocumentReader pdfReader = new ParagraphPdfDocumentReader(pdfResource);
                docs.addAll(pdfReader.read());
            } catch (IllegalArgumentException e) {
                PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource);
                docs.addAll(pdfReader.read());
            }

            for (Document doc : docs) {
                 docs2.add(new Document(sanitizeInput(doc.getContent())));
            }

            if (!docs2.isEmpty()) {
                docs2 = summaryDocuments(keywordDocuments(docs2));
                allDocs.addAll(docs2);
            }
        }


        return allDocs;
    }

    private String sanitizeInput(String input) {
        return input.replace("\u0000", ""); // Replace null bytes with an empty string
    }

    List<Document> keywordDocuments(List<Document> documents) {
        log.info("Process keyword");
        KeywordMetadataEnricher keywordEnricher = new KeywordMetadataEnricher(chatModel, 3);
        return keywordEnricher.apply(documents);
    }

    List<Document> summaryDocuments(List<Document> documents) {
        log.info("Process summary");
        return documents;
//        SummaryMetadataEnricher summaryEnricher = new SummaryMetadataEnricher(chatModel,
//                List.of(SummaryMetadataEnricher.SummaryType.CURRENT)
//        );
//        return summaryEnricher.apply(documents);
    }


    public void importPdf() {
        TokenTextSplitter splitter = new TokenTextSplitter();
//        TokenTextSplitter splitter = new TokenTextSplitter(800, 350, 5, 10000, true);
        try {
            vectorStore.write(splitter.split(loadPdfAsDocuments()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info("完成importPdf");
    }



}

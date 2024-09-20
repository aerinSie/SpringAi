package com.example.spring_ai;

import com.example.spring_ai.service.PdfService;
import com.example.spring_ai.service.SearchService;
import com.example.spring_ai.service.TxtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringAiApplicationTests {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private TxtService txtService;

    @Autowired
    private SearchService searchService;

    @Test
    void importPdf() {
        pdfService.importPdf();
    }

    @Test
    void readPdfFile() throws IOException {
        var list = pdfService.loadPdfAsDocuments();
        System.out.println(list);
    }

    @Test
    void importTxt() {
        txtService.importText();
    }

    @Test
    void readTxtFile() throws IOException {
        var list = txtService.loadTextAsDocuments();
        System.out.println(list);
    }

    /**
     * RAG對話
     * @throws IOException
     */
    @Test
    void chat() throws IOException {
        var result = searchService.ragSearch("什麼是spring ai?");
        System.out.println(result);
    }

}

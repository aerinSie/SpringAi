package com.example.spring_ai.controller;

import com.example.spring_ai.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/ai/pdf")
@RequiredArgsConstructor
@Slf4j
public class PdfController {

    private final PdfService pdfService;

    @GetMapping("importpdf")
    public void importPdf(){
        pdfService.importPdf();
    }

    @GetMapping("readpdf")
    public List<Document> readPdfFile() throws IOException {
        return pdfService.loadPdfAsDocuments();
    }


}

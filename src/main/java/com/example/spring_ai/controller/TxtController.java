package com.example.spring_ai.controller;

import com.example.spring_ai.service.PdfService;
import com.example.spring_ai.service.TxtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ai/txt")
@RequiredArgsConstructor
@Slf4j
public class TxtController {

    private final TxtService txtService;

    @GetMapping("importtext")
    public void importText(){
        txtService.importText();
    }

    @GetMapping("readtext")
    public List<Document> readTextFile(){
        return txtService.loadTextAsDocuments();
    }
}

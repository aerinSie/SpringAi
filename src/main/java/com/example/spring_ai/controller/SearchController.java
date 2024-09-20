package com.example.spring_ai.controller;


import com.example.spring_ai.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/ai/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/rag")
    public ResponseEntity<Resource> search(
            @RequestParam(required = false, defaultValue = "什麼是spring ai?") String query) {
        Resource resource;
        try {
            resource = searchService.search(query);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"result.md\"")
                .body(resource);
    }

    @GetMapping("/ragSearch")
    public ResponseEntity<String> ragSearch(
            @RequestParam(required = false, defaultValue = "什麼是spring ai?") String query) throws Exception {
        return ResponseEntity.ok(searchService.ragSearch(query));
    }

}

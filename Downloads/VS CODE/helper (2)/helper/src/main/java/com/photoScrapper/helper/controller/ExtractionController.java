package com.photoScrapper.helper.controller;


import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photoScrapper.helper.dto.ContactDTO;
import com.photoScrapper.helper.services.ExtractionService;

@RestController
@RequestMapping("/api")
public class ExtractionController {

    private final ExtractionService extractionService;

    public ExtractionController(ExtractionService extractionService) {
        this.extractionService = extractionService;
    }


    @PostMapping(
        value = "/extract",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ContactDTO> extract(@RequestParam("file") MultipartFile file) {
        return extractionService.extract(file);
    }
}
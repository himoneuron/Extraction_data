package com.photoScrapper.helper.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.photoScrapper.helper.dto.ContactDTO;
import com.photoScrapper.helper.services.ContactService;
import com.photoScrapper.helper.services.ExtractionService;
// No longer importing TikaService, as you noted.
// import com.photoScrapper.helper.services.TikaService; 

@RestController
@RequestMapping("/ingest")
public class IngestController {

    // Removed TikaService dependency
    private final ExtractionService extractionService;
    private final ContactService contactService;

    public IngestController(ExtractionService extractionService,
                            ContactService contactService) {
        this.extractionService = extractionService;
        this.contactService = contactService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ContactDTO>> ingest(
            @RequestPart("files") List<MultipartFile> files) throws Exception {

        List<ContactDTO> out = new ArrayList<>();

        for (MultipartFile file : files) {

            String text = extractText(file);
            if (text == null) text = "";

            // ✅ Parse all candidates
            List<ContactDTO> extracted = extractionService.extractCandidates(text);

            // ✅ Save into DB
            contactService.saveAll(extracted, file.getOriginalFilename(), text);

            out.addAll(extracted);
        }

        return ResponseEntity.ok(out);
    }


    /**
     * Extract text using Tika only.
     */
    private String extractText(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            // This call is now unambiguous. It correctly calls the
            // extractText(InputStream) method in ExtractionService.
            return extractionService.extractText(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
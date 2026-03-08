package com.photoScrapper.helper.services;

import java.io.InputStream;
import java.util.*;
import java.util.regex.*;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.ocr.TesseractOCRParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.photoScrapper.helper.configurations.OcrProperties;
import com.photoScrapper.helper.dto.ContactDTO;

@Service
public class ExtractionService {

    @Autowired
    private OcrProperties ocrProps; // This is now correctly injected

    // Tika components, instantiated once
    private final TikaConfig config = TikaConfig.getDefaultConfig();
    private final AutoDetectParser parser = new AutoDetectParser(config);

    // Optimized Regex Patterns
    private static final Pattern PHONE = Pattern.compile(
            "(?<!\\d)(?:\\+\\d{1,3}[\\s.-]?)?(?:\\(?\\d{2,4}\\)?[\\s.-]?)?\\d{3,4}[\\s.-]?\\d{4}(?:\\s*x\\d{1,5})?(?!\\d)"
    );
    private static final Pattern EMAIL = Pattern.compile(
            "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[A-Za-z]{2,}"
    );
    private static final Pattern NAME_REGEX = Pattern.compile(
            "^[A-Z][\\p{L}'\\.\\-]*(?:\\s+[A-Z][\\p{L}'\\.\\-]*)*(?:[,\\(].*)?$"
    );
    private static final Pattern NOT_A_NAME_REGEX = Pattern.compile(
            "(?i)^(Res\\.|Off\\.|Mob|Phone|Email|Add:|Tel:).*"
    );

    /**
     * Main public method for the /api/extract endpoint.
     */
    public List<ContactDTO> extract(MultipartFile file) {
        try {
            String text = extractText(file.getInputStream());
            System.out.println("✅ Extracted text:\n" + text);
            return extractCandidates(text);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Consolidated text extraction logic (formerly in TikaService).
     */
  public String extractText(InputStream in) throws Exception {
    // 1) Config: language, timeouts, etc. stay on the config object
    TesseractOCRConfig ocrConfig = new TesseractOCRConfig();
    ocrConfig.setLanguage(ocrProps.getLanguage());

    // 2) Parser: file system paths (tesseract exe dir + tessdata) live on the parser
    TesseractOCRParser ocrParser = new TesseractOCRParser();

    String tessdataPath = ocrProps.getTesseractPath(); // this is your tessdata directory
    if (tessdataPath != null && !tessdataPath.isBlank()) {
        // Set tessdata path explicitly (e.g., C:\Program Files\Tesseract-OCR\tessdata)
        ocrParser.setTessdataPath(tessdataPath);

        // If the value ends with "tessdata", set the Tesseract install dir too
        if (tessdataPath.endsWith("tessdata")) {
            java.io.File parent = new java.io.File(tessdataPath).getParentFile();
            if (parent != null) {
                ocrParser.setTesseractPath(parent.getAbsolutePath());
            }
        }
    }

    ParseContext context = new ParseContext();
    context.set(TesseractOCRConfig.class, ocrConfig);
    // Provide the customized parser instance to the context as well
    context.set(TesseractOCRParser.class, ocrParser);

    BodyContentHandler handler = new BodyContentHandler(-1);
    Metadata metadata = new Metadata();

    // Use your existing AutoDetectParser field; it will consult the context
    parser.parse(in, handler, metadata, context);
    return handler.toString();
}

    /**
     * Parses a raw text block and extracts a list of contact candidates.
     */
    public List<ContactDTO> extractCandidates(String text) {
        // This line is now fixed (correct '||' operator)
        if (text == null || text.isBlank()) return List.of();

        List<ContactDTO> list = new ArrayList<>();

        for (String block : splitRecords(text)) {
            String name = findName(block);
            if (name == null) continue;

            Matcher mPhone = PHONE.matcher(block);
            Matcher mEmail = EMAIL.matcher(block);
            String email = mEmail.find()? mEmail.group() : null;

            while (mPhone.find()) {
                String number = normalizePhone(mPhone.group());
                if (!number.isBlank()) {
                    ContactDTO dto = new ContactDTO(name.trim(), number, block);
                    dto.setEmail(email);
                    list.add(dto);
                }
            }
        }
        return list;
    }

    /**
     * Splits the full text into logical blocks.
     */
    private List<String> splitRecords(String text) {
        return Arrays.stream(text.split("\\n{2,}"))
          .map(String::trim)
          .filter(s ->!s.isBlank())
          .toList();
    }

    /**
     * OPTIMIZED name-finding logic.
     */
    private String findName(String block) {
        for (String line : block.split("\\R")) {
            line = line.trim();
            if (line.isBlank()) continue;

            if (NOT_A_NAME_REGEX.matcher(line).matches()) continue;

            if (NAME_REGEX.matcher(line).matches()) {
                return cleanName(line);
            }
        }
        return null;
    }

    /**
     * Helper method to clean suffixes from a matched name line.
     * This is the complete version of the method.
     */
    private String cleanName(String line) {
        int commaIndex = line.indexOf(',');
        if (commaIndex!= -1) {
            return line.substring(0, commaIndex).trim();
        }
        if (line.endsWith("(Late)")) {
            return line.substring(0, line.length() - "(Late)".length()).trim();
        }
        return line;
    }

    /**
     * Cleans a raw phone number string.
     */
    private String normalizePhone(String raw) {
        String s = raw.replaceAll("[^+\\d]", "");
        String digits = s.startsWith("+")? s.substring(1) : s;
        return (digits.length() < 8)? "" : s; // return the cleaned value
    }
}
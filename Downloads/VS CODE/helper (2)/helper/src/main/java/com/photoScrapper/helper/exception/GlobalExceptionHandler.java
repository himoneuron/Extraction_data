package com.photoScrapper.helper.exception;

import java.io.IOException;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

// ADDED: This annotation registers the class as a global exception handler
// for all @RestControllers.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleMaxUpload(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
               .body(Map.of(
                        "error", "File too large",
                        "message", "The uploaded file exceeds the maximum permitted size.",
                        "details", "See 'spring.servlet.multipart.*' settings."
                ));
    }

    // ADDED: Specific handler for Tika/OCR errors
    @ExceptionHandler({TikaException.class, IOException.class})
    public ResponseEntity<Map<String, Object>> handleParsingException(Exception ex) {
        ex.printStackTrace(); // Log the full trace
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
               .body(Map.of(
                        "error", "File processing failed",
                        "message", "The application could not parse the provided file.",
                        "details", ex.getMessage()
                ));
    }

    // ADDED: Specific handler for database-related errors (e.g., finding non-existent entity)
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(Map.of(
                        "error", "Resource not found",
                        "message", ex.getMessage()
                ));
    }


    /**
     * A general-purpose handler for all other exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAny(Exception ex) {
        
// Log the full stack trace for internal debugging
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(Map.of(
                        "error", ex.getClass().getSimpleName(),
                        "message", "An unexpected internal server error occurred.",
                        "details", ex.getMessage()
                ));
    }
}
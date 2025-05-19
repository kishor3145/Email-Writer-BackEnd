package com.email.email_writer_kd.app;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailGeneratorController {
    private final EmailGenerateService emailGenerateService;

    public EmailGeneratorController(EmailGenerateService emailGenerateService) {
        this.emailGenerateService = emailGenerateService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateEmail(@Valid @RequestBody EmailRequest emailRequest) {
        try {
            String response = emailGenerateService.generateEmailReply(emailRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("VALIDATION_ERROR", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ErrorResponse("SERVER_ERROR", "Failed to generate email")
            );
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                new ErrorResponse("VALIDATION_ERROR", errorMessage)
        );
    }

    // Simple error response DTO
    private record ErrorResponse(String code, String message) {}
}
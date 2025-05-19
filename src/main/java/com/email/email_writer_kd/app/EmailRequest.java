package com.email.email_writer_kd.app;


import jakarta.validation.constraints.NotBlank;

public class EmailRequest {
    @NotBlank(message = "Email content cannot be empty")
    private String emailContents;

    private String tone;

    // Getters and Setters

    public String getEmailContents() {
        return emailContents;
    }

    public void setEmailContents(String emailContents) {
        this.emailContents = emailContents;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }
}

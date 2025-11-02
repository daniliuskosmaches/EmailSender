package com.example.emailsender.Controller;

import com.example.emailsender.Entity.EmailEntity;
import com.example.emailsender.Service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/booking")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailEntity emailEntity) {
        emailService.SendEmail(emailEntity);
        return ResponseEntity.ok("Письмо отправлено, спасибо что выбрали нашу компанию в ближайшее время с вами свяжуться");
    }
}


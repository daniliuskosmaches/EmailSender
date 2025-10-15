package com.example.emailsender.Controller;

import com.example.emailsender.Entity.EmailEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class EmailController {
    @RequestMapping
    public ResponseEntity<?> sendEmail(EmailEntity emailEntity) {
        return ResponseEntity.ok().build();


    }

}

package com.test.emailer.controller;


import com.test.emailer.model.Message;
import com.test.emailer.service.mail.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email/")
public class ApiController {
    @Autowired
    EmailServiceImpl emailService;

    @GetMapping(value = "send")
    public ResponseEntity<String> send() {
        Message message = new Message();
        emailService.sendMail(message);
        return ResponseEntity.ok("ok");
    }
}

package com.test.emailer.model;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    private String salutation;
    private String sentFrom;
    private String sentTo;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String content;
    private String closing;
}

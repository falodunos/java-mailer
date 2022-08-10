package com.test.emailer.service.mail;

import com.test.emailer.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendMail(Message message) {
        String template = buildTemplate(message);
        this.sendSimpleMessage("falodunosolomon@gmail.com", "Notification", template);
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = this.composeMessage(to, subject, text);
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String... templateArgs) {
//        String text = String.format(template.getText(), templateArgs);
//        sendSimpleMessage(to, subject, text);
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private SimpleMailMessage composeMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] tos = to.split(",");

        if (isValid(tos[0])) {
            message.setTo(tos[0]);
        }

        if (tos.length > 1) {
            for (int i = 1; i < tos.length; i++) {
                if (isValid(tos[i])) {
                    message.setTo(tos[i]);
                }
            }
        }

        message.setSubject(subject);
        message.setText(text);
        message.setSentDate(new Date());

        return message;
    }

    private boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private String buildTemplate(Message message) {
        String text = "";
//        text = message.getSalutation() + ", \n";
//        text += message.getContent() + "\n\n";
//        text += message.getClosing();

        text = "Hello Team, \n";
        text += "Please note that the node or service with name service is back on and accessible. \n";
        text += "Response code: '00' \n";
        text += "Response message : 'Active' \n\n";
        text += "Thank you and best regards. \n\nProduction Team";

        return text;
    }
}
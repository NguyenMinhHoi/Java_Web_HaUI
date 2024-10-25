package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private EmailConfigurationService emailConfigService;

    public void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException {
        JavaMailSender emailSender = emailConfigService.getJavaMailSender();
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }

    public void sendMerchantRegistrationAcceptance(String to, String merchantName) throws MessagingException {
    String subject = "Welcome to Our Merchant Program!";
    String htmlContent = String.format(
        "<html>" +
        "<head>" +
        "<style>" +
        "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
        "h1 { color: #4a4a4a; }" +
        ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
        ".header { background-color: #f8f9fa; padding: 20px; text-align: center; }" +
        ".content { padding: 20px; }" +
        ".footer { background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 12px; }" +
        "</style>" +
        "</head>" +
        "<body>" +
        "<div class='container'>" +
        "<div class='header'>" +
        "<h1>Welcome to Our Merchant Program!</h1>" +
        "</div>" +
        "<div class='content'>" +
        "<h2>Congratulations, %s!</h2>" +
        "<p>Your application to become a merchant has been accepted.</p>" +
        "<p>You now have access to our merchant tools and can start selling your products.</p>" +
        "<p>If you have any questions, please don't hesitate to contact our support team.</p>" +
        "</div>" +
        "<div class='footer'>" +
        "<p>Best regards,<br>Your E-commerce Team</p>" +
        "</div>" +
        "</div>" +
        "</body></html>",
        merchantName
    );
    sendHtmlMessage(to, subject, htmlContent);
}

    public void sendRoyalMerchantRegistrationAcceptance(String to, String merchantName) throws MessagingException {
        String subject = "Congratulations on Becoming a Royal Merchant!";
        String htmlContent = String.format(
            "<html><body>" +
            "<h1>Welcome to the Royal Merchant Program, %s!</h1>" +
            "<p>We are thrilled to inform you that your application for Royal Merchant status has been accepted.</p>" +
            "<p>As a Royal Merchant, you now have access to exclusive benefits, including:</p>" +
            "<ul>" +
            "<li>Priority customer support</li>" +
            "<li>Lower transaction fees</li>" +
            "<li>Advanced analytics tools</li>" +
            "<li>Exclusive promotional opportunities</li>" +
            "</ul>" +
            "<p>Our Royal Merchant support team will contact you shortly to guide you through your new privileges.</p>" +
            "<p>Thank you for your outstanding performance and commitment to our platform!</p>" +
            "<p>Best regards,<br>Your E-commerce Executive Team</p>" +
            "</body></html>",
            merchantName
        );
        sendHtmlMessage(to, subject, htmlContent);
    }
}
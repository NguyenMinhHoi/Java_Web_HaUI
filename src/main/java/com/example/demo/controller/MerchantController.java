package com.example.demo.controller;


import com.example.demo.model.Merchant;
import com.example.demo.service.MerchantService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/merchant")
@CrossOrigin("*")
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping("/test-approval")
    public ResponseEntity<?> testApproval() {
        Merchant testMerchant = new Merchant();
        testMerchant.setEmail("tecaca64@gmail.com");  // Replace with your email
        testMerchant.setName("Test Merchant");
        merchantService.approveMerchantRegistration(testMerchant);
        return ResponseEntity.ok("Approval email sent successfully");
    }

}

package com.example.demo.service.impl;

import com.example.demo.model.Merchant;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.repository.MerchantRepository;
import com.example.demo.service.MerchantService;
import com.example.demo.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private EmailService emailService;
    // Inject the OrderServiceImpl

    @Override
    public List<Merchant> findAll() {
        return List.of();
    }

    @Override
    public Merchant findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Merchant save(Merchant entity) {

        return null;
    }

    @Override
    public void approveMerchantRegistration(Merchant merchant) {
        // Logic to approve the merchant
        // ...

        // Send acceptance email
        try {
            emailService.sendMerchantRegistrationAcceptance(merchant.getEmail(), merchant.getName());
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void upgradeToRoyalMerchant(Merchant merchant) {
        // Logic to upgrade the merchant to Royal status
        // ...

        // Send Royal Merchant acceptance email
        try {
            emailService.sendRoyalMerchantRegistrationAcceptance(merchant.getEmail(), merchant.getName());
        } catch (MessagingException e) {
            // Handle the exception (e.g., log it)
            e.printStackTrace();
        }
    }

}

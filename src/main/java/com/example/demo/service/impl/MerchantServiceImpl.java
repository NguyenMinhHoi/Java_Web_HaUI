package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.MerchantRepository;
import com.example.demo.service.MerchantService;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import com.example.demo.utils.CommonUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private EmailService emailService;
    @Qualifier("userService")
    @Autowired
    private UserService userService;
    @Autowired
    private RoleServiceImpl roleServiceImpl;
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

    @Override
    public Merchant createMerchant(Merchant merchant) {
        // Add any necessary validation or business logic here
        Optional<User> user = userService.findById(merchant.getUser().getId());
        if(user.isPresent()){
            merchant.setUser(user.get());
            Role role = roleServiceImpl.findByName("ROLE_MERCHANT");
            user.get().getRoles().add(role);
            userService.save(user.get());
            merchantRepository.save(merchant);
        }else{
            throw new RuntimeException("User not found");
        }
        return merchantRepository.save(merchant);
    }

    @Override
    public Merchant getMerchantById(Long userId) {
        Merchant merchant = merchantRepository.findByUserId(userId);
        if(!CommonUtils.isEmpty(merchant)){
            merchant.setUser(null);
        }
        return merchant;
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

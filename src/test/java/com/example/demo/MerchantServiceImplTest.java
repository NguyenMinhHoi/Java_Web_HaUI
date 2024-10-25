package com.example.demo;

import com.example.demo.model.Merchant;
import com.example.demo.repository.MerchantRepository;
import com.example.demo.serivce.impl.MerchantServiceImpl;
import com.example.demo.service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class MerchantServiceImplTest {

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private MerchantServiceImpl merchantService;
    
    @Test
    void shouldSuccessfullyApproveValidMerchantRegistration() throws MessagingException {
        // Arrange
        Merchant merchant = new Merchant();
        merchant.setEmail("hoin1999999@gmail.com");
        merchant.setName("Test Merchant");
        // Act
        merchantService.approveMerchantRegistration(merchant);

        // Assert
        Mockito.verify(emailService).sendMerchantRegistrationAcceptance(merchant.getEmail(), merchant.getName());
    }
}

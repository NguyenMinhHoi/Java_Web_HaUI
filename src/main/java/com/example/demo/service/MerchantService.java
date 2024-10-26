package com.example.demo.service;

import com.example.demo.model.Merchant;

public interface MerchantService extends GenerateService<Merchant> {
     void approveMerchantRegistration(Merchant merchant);

}

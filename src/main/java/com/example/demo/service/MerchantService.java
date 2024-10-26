package com.example.demo.service;

import com.example.demo.model.Merchant;

import com.example.demo.model.Merchant;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MerchantService extends GenerateService<Merchant> {
    void approveMerchantRegistration(Merchant merchant);
}

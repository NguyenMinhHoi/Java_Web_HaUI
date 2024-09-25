package com.example.demo.serivce.impl;

import com.example.demo.model.Merchant;
import com.example.demo.repository.MerchantRepository;
import com.example.demo.serivce.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

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
    public void save(Merchant entity) {

    }
}

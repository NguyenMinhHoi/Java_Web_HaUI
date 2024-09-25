package com.example.demo.serivce.impl;

import com.example.demo.model.Voucher;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.serivce.VoucherService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Voucher> findAll() {
        return List.of();
    }

    @Override
    public Voucher findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Voucher entity) {

    }
}

package com.example.demo.service.impl;

import com.example.demo.model.Address;
import com.example.demo.model.Product;
import com.example.demo.repository.AddressRepository;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressSerivceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
         addressRepository.deleteById(id);
    }

    @Override
    public Address save(Address entity) {
         addressRepository.save(entity);
         return null;
    }
}

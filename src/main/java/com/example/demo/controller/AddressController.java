package com.example.demo.controller;


import com.example.demo.serivce.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Controller
@RequestMapping("/addresses")
public class AddressController {

       @Autowired
       private AddressService addressService;


}

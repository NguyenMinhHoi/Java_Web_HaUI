package com.example.demo.dto;

import com.example.demo.model.Variant;
import lombok.Data;

import java.util.List;
import java.util.HashMap;

@Data
public class OrderDTO {
    private List<Variant> variants;
    private Long userId;
    private Long merchantNumber;
    private HashMap<String, String> detail;
}
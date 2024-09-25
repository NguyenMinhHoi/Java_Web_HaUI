package com.example.demo.model;

import com.example.demo.utils.enumeration.VoucherType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private VoucherType voucherType;

    @ManyToOne(fetch = FetchType.LAZY)
    private VoucherCondition voucherCondition;

    private Double valueCondition;

    private Double discount;
    
}

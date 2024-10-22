package com.example.demo.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String name;

     private String description;

     private Long sold;

     private Boolean isDiscount;

     @ManyToOne
     private Merchant merchant;

     @OneToMany
     private Set<Image> image;

     private Double rating;

     @OneToMany
     private Set<GroupOption> groupOptions;

     @ManyToOne
     private Category category;

}

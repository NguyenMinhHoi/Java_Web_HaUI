package com.example.demo.repository;

import com.example.demo.model.OptionProduct;
import com.example.demo.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VariantRepository extends JpaRepository<Variant,Long> {
    Variant findVariantByOptions(Set<OptionProduct> optionProductList);

    List<Variant> findVariantByProductId(Long id);

    Variant findVariantById(Long id);
}

package com.example.demo.serivce;

import java.util.List;

public interface GenerateService<E> {
    List<E> findAll();

    E findById(Long id);

    void deleteById(Long id);

    void save(E entity);
}

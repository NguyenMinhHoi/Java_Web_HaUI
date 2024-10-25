package com.example.demo.serivce;


import com.example.demo.model.Role;


public interface RoleService {
    Iterable<Role> findAll();

    void save(Role role);

    Role findByName(String name);
}

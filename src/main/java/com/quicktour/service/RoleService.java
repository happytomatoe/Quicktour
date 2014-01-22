package com.quicktour.service;

import com.quicktour.entity.Role;
import com.quicktour.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByRole(String roleName){
        return roleRepository.findByRole(roleName);
    }

    public Role getRole(Integer id) {
        return roleRepository.findOne(id);
    }
}

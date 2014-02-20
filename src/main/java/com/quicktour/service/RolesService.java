package com.quicktour.service;

import com.quicktour.entity.Role;
import com.quicktour.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Roman Lukash
 */
@Service
@Transactional
public class RolesService {
    @Autowired
    RoleRepository roleRepository;

    public Role findOne(Integer id) {
        return roleRepository.findOne(id);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}

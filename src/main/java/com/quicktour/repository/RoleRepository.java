package com.quicktour.repository;

import com.quicktour.entity.Role;
import com.quicktour.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByRole(String role);
}

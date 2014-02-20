package com.quicktour.repository;

import com.quicktour.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Roman Lukash
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}

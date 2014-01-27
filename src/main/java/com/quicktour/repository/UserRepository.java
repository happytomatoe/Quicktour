package com.quicktour.repository;

import com.quicktour.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByLogin(String login);

    public User findByEmail(String email);

}

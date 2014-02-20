package com.quicktour.repository;

import com.quicktour.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);


    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users WHERE  TIMESTAMPDIFF(HOUR,`create_time`,CURRENT_TIMESTAMP())> 48 AND `active`=0", nativeQuery = true)
    void deleteExpiredNotActiveUsers();

}

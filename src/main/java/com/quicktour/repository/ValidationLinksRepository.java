package com.quicktour.repository;

import com.quicktour.entity.ValidationLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ValidationLinksRepository extends JpaRepository<ValidationLink, Integer> {
    public java.util.List<ValidationLink> findByUserId(int id);

    public ValidationLink findByUrl(String s);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM  validation_links WHERE TIMESTAMPDIFF(HOUR,`create_time`,CURRENT_TIMESTAMP())> 2 ", nativeQuery = true)
    void deleteExpiredLinks();
}

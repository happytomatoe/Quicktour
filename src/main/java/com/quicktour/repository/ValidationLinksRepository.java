package com.quicktour.repository;

import com.quicktour.entity.ValidationLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ValidationLinksRepository extends JpaRepository<ValidationLink, Integer> {
    public ValidationLink findByUserId(int id);

    public ValidationLink findByValidationLink(String s);
}

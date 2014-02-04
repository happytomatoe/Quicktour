package com.quicktour.repository;

import com.quicktour.entity.ValidationLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationLinksRepository extends JpaRepository<ValidationLink, Integer> {
    public ValidationLink findByUserId(int id);

    public ValidationLink findByUrl(String s);
}

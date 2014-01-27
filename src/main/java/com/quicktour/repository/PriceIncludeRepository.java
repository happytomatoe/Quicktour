package com.quicktour.repository;

import com.quicktour.entity.PriceDescription;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: student
 * Date: 02.12.13
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public interface PriceIncludeRepository extends JpaRepository<PriceDescription, Integer> {
    PriceDescription findByDescription(String description);
}

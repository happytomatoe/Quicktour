package com.quicktour.repository;

import com.quicktour.entity.DiscountDependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author Roman Lukash
 */
public interface DiscountDependencyRepository extends JpaRepository<DiscountDependency, Integer> {
    @Query(value = " SELECT concat(\"orders.\",COLUMN_NAME) FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_SCHEMA = ?1 AND TABLE_NAME =  'orders' AND\n" +
            "DATA_TYPE REGEXP 'int$|double|real|float|decimal'\n" +
            "UNION ALL\n" +
            "SELECT  concat(\"users.\",COLUMN_NAME)  FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_SCHEMA = ?1 AND TABLE_NAME =  'users' AND  " +
            "DATA_TYPE REGEXP 'int$|double|real|float|decimal'\n", nativeQuery = true)
    List<String> getNumberTypeColumnNames(String dbName);

}

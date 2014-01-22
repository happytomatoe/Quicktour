package com.quicktour.repository;

import com.quicktour.entity.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcursionRepository extends JpaRepository<Excursion, Integer> {
}

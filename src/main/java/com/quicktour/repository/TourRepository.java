package com.quicktour.repository;

import com.quicktour.entity.TourInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<TourInfo, Integer> {
}

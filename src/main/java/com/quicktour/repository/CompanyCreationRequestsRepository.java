package com.quicktour.repository;

import com.quicktour.entity.CompanyCreationRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCreationRequestsRepository extends JpaRepository<CompanyCreationRequests, Integer> {
}

package com.quicktour.repository;

import com.quicktour.entity.Company;
import com.quicktour.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ToursRepository extends JpaRepository<Tour, Integer> {
    Tour findByTourId(int id);


    @Query("SELECT t FROM Tour t WHERE t.company=?1 AND t.discountPolicies IS NOT EMPTY ")
    List<Tour> findByCompanyAndDiscountPoliciesIsNotEmpty(Company company);

    @Query("SELECT t FROM Tour t WHERE t.company=?1 AND t.discountPolicies IS EMPTY ")
    List<Tour> findByCompanyAndDiscountPoliciesIsEmpty(Company company);

    Page<Tour> findByActiveTrue(Pageable pageable);

    List<Tour> findByTourIdIn(List<Integer> ids);

    //search tours by country
    @Query("select distinct t from Tour as t inner join t.toursPlaces as p where p.country = ?1")
    Page<Tour> findToursByCountry(String country, Pageable pageable);

    //search tours by place name
    @Query("select distinct t from Tour as t inner join t.toursPlaces as p where p.name = ?1")
    Page<Tour> findToursByPlaceName(String placeName, Pageable pageable);

    //search tours by price
    @Query("select t from Tour as t where t.price>?1 and t.price<?2")
    Page<Tour> findToursByPrice(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);


    @Query("select distinct t from Tour as t " +
            "inner join t.tourInfo as ti inner join ti.orders as o WHERE t.active=true " +
            "group by ti.tour order by avg(o.vote) desc")
    Page<Tour> findFamousTours(Pageable pageable);
}

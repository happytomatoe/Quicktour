package com.quicktour.repository;

import com.quicktour.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    @Query("select distinct p.country from Place p order by p.country")
    List<String> findCountries();

    @Query("select distinct p.name from Place p order by p.name")
    List<String> findPlaceNames();

    @Query("select distinct p.name from Place p where p.country=?1 order by p.name")
    List<String> findPlacesByCountry(String country);
}

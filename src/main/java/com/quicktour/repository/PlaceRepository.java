package com.quicktour.repository;

import com.quicktour.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    @Query(value = "SELECT country , GROUP_CONCAT( places.name )FROM  places " +
            "INNER JOIN tours_places " +
            "ON places.place_id=tours_places.places_id " +
            "INNER JOIN tours " +
            "ON tours.tour_id=tours_places.tours_id " +
            "WHERE tours.active=1 " +
            "GROUP BY country ", nativeQuery = true)
    List<Object[]> findCountriesWithPlaces();

}

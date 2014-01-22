package com.quicktour.service;

import com.quicktour.entity.Place;
import com.quicktour.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: student
 * Date: 04.12.13
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    public List<String> findCountries() {
        return placeRepository.findCountries();
    }

    public List<String> findPlacesNames() {
        return placeRepository.findPlaceNames();
    }

    public List<String> findPlacesByCountry(String country) {
        return placeRepository.findPlacesByCountry(country);
    }

    public List<Place> savePlases(List<Place> places) {
        return placeRepository.save(places);
    }

}

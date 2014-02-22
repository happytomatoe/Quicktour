package com.quicktour.service;

import com.quicktour.dto.Country;
import com.quicktour.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: student
 * Date: 04.12.13
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */

@Service
@Transactional
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    public List<Country> findCountriesWithPlaces() {
        List<Object[]> result = placeRepository.findCountriesWithPlaces();
        ArrayList<Country> countries = new ArrayList<>();
        for (Object[] countryWithPlaces : result) {
            countries.add(new Country((String) countryWithPlaces[0], new HashSet<>(Arrays.asList(((String) countryWithPlaces[1]).split(",")))));
        }
        return countries;
    }

    public List<String> extractPlaces(List<Country> countriesWithPlaces) {
        ArrayList<String> places = new ArrayList<>();
        for (Country country : countriesWithPlaces) {
            for (String place : country.getPlaces()) {
                places.add(place);
            }
        }
        return places;
    }
}

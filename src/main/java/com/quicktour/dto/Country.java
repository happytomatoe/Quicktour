package com.quicktour.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Roman Lukash
 */
public class Country {
    private String name;
    private Set<String> places;

    public Country(String name, Set<String> places) {
        this.name = name;
        this.places = places;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = new HashSet<>(Arrays.asList(places.split(",")));
    }
}

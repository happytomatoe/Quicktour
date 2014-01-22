package com.quicktour.entity;

import javax.validation.Valid;
import java.util.List;

public class CompleteTourInfo {

    @Valid
    private Tour tour;

    private List<String> priceIncludes;   // TODO change from String to Price includes bean

    private List<TourInfo> tourInfo;

    private List<Place> places;

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public List<String> getPriceIncludes() {
        return priceIncludes;
    }

    public void setPriceIncludes(List<String> priceIncludes) {
        this.priceIncludes = priceIncludes;
    }

    public List<TourInfo> getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(List<TourInfo> tourInfo) {
        this.tourInfo = tourInfo;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}

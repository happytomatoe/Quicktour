package com.quicktour.service;

import com.quicktour.entity.TourInfo;
import com.quicktour.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: student
 * Date: 13.12.13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    public List<TourInfo> saveTour(List<TourInfo> tourInfoList) {
        return tourRepository.save(tourInfoList);
    }

}

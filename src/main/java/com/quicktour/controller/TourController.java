package com.quicktour.controller;

import com.quicktour.entity.Place;
import com.quicktour.entity.Tour;
import com.quicktour.service.ToursService;
import com.quicktour.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller that process the information for the page of concrete tour
 *
 * @author Kolya Yanchiy
 */
@RequestMapping("/tour")
@Controller
public class TourController {

    private final Logger logger = LoggerFactory.getLogger(TourController.class);
    @Autowired
    private ToursService toursService;
    @Autowired
    private UsersService usersService;

    /**
     * Method get information about current tour, it's min price,
     * max discount and users star rating from database and show it to user
     *
     * @param id  - identificator of the tour which user want to review
     * @param map - model that will be mapped to the place-extended tile
     * @return view place-extended
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getTours(@PathVariable("id") int id, ModelMap map) {

        Tour tour = toursService.findTourById(id);
        BigDecimal minPrice = toursService.findMinPrice(tour);
        double maxDiscount = toursService.findMaxDiscount(tour);

        map.addAttribute("tour", tour);
        map.addAttribute("minPrice", minPrice);
        map.addAttribute("maxDiscount", maxDiscount);
        map.addAttribute("user", usersService.getCurrentUser());


        return "viewTour";
    }


    /**
     * Methods gets the required information for the components on tour page and
     * puts them on the container that processed on the client side
     *
     * @param id - identificator of the tour infomation about we are interested in
     * @return map (which will be convert to JSON object) with all required information
     * for GoogleMap and PanoramioWidget
     */
    @RequestMapping(value = "/{id}/getData", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> getData(@RequestParam("id") int id) {
        Tour tour = toursService.findTourByIdWithPlaces(id);
        logger.debug("Find tour {}", tour);
        List<Place> toursPlaces = tour.getToursPlaces();
        Map<String, Object> map = new HashMap<>();
        map.put("places", toursPlaces);
        map.put("travelType", tour.getTravelType());

        return map;
    }


}

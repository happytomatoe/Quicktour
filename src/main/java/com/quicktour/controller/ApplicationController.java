package com.quicktour.controller;

import com.quicktour.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller that handles all activity on main page
 *
 * @author Bogdan Shpakovsky
 */

@Controller
public class ApplicationController {
    Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    @Autowired
    DiscountPolicyService discountPolicyService;
    @Autowired
    UsersService usersService;
    @Autowired
    private ToursService toursService;
    @Autowired
    private PlaceService placeService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDatePropertyEditor());
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
    }

    /**
     * Method that runs when user come into portal and redirect it to url set in return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:page/0";
    }

    /**
     * add all tours to main page of application
     *
     * @param map     map of model attributes
     * @param pageNum number of current user page for pagination
     * @return string which describe representation of main page
     */
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public String showPage(ModelMap map,
                           @PathVariable("page") int pageNum) {

        addSidebarAttributes(map);
        Page page = toursService.findAllToursAndCut(pageNum);

        map.addAttribute("page", page);

        return "index-extended";
    }

    /**
     * run when user select one of the country on sidebar
     * add filtered by country tours to the model of main page
     */
    @RequestMapping(value = "/country/{country}/{page}", method = RequestMethod.GET)
    public String indexCountry(@PathVariable("country") String country,
                               @PathVariable("page") int pageNum,
                               ModelMap map) {
        map.addAttribute("page", toursService.findToursByCountryAndCut(country, pageNum));
        addSidebarAttributes(map);
        return "index-extended";
    }

    /**
     * run when user select one of the place on sidebar
     * add filtered by place tours to the model of main page
     */
    @RequestMapping(value = "/place/{place}/{page}", method = RequestMethod.GET)
    public String indexPlace(@PathVariable("place") String place,
                             @PathVariable("page") int pageNum,
                             ModelMap map) {
        map.addAttribute("page", toursService.findToursByPlacesAndCut(place, pageNum));
        addSidebarAttributes(map);
        return "index-extended";
    }

    /**
     * run when user select one of the place on sidebar
     * add filtered by place tours to the model of main page
     */
    @RequestMapping(value = "/price/{min}/{max}/{page}", method = RequestMethod.GET)
    public String findByPriceView(@PathVariable("min") Integer min,
                                  @PathVariable("max") Integer max,
                                  @PathVariable("page") int pageNum,
                                  ModelMap map) {
        map.addAttribute("page", toursService.findToursByPriceAndCut(min, max, pageNum));
        addSidebarAttributes(map);
        return "index-extended";
    }

    /**
     * run when user begin extended search for tours
     */
    @RequestMapping(value = "/filter/results/{page}", method = RequestMethod.POST)
    public String filter(@PathVariable("page") int pageNum,
                         @RequestParam("country") String country,
                         @RequestParam("place") String place,
                         @RequestParam("minDate") Date minDate,
                         @RequestParam("maxDate") Date maxDate,
                         @RequestParam("minPrice") Integer minPrice,
                         @RequestParam("maxPrice") Integer maxPrice,
                         ModelMap map) {
        map.addAttribute("page", toursService.extendFilter(country, place, minDate, maxDate,
                minPrice, maxPrice, pageNum));
        addSidebarAttributes(map);
        return "index-extended";
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/placesByCountry")
    @ResponseBody
    private Map<String, List<String>> getPlacesByCountry(@RequestParam("country") String country) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        logger.debug("Trying to find places by country {}.{}", country, placeService);
        List<String> places = placeService.findPlacesByCountry(country);
        logger.debug("Found {}", places);
        map.put("places", places);
        return map;
    }

    private void addSidebarAttributes(ModelMap map) {
        map.addAttribute("countries", placeService.findCountries());

        map.addAttribute("places", placeService.findPlacesNames());

        map.addAttribute("famousTours", toursService.findFamousTours());
    }


}

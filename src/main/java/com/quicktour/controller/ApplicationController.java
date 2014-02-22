package com.quicktour.controller;

import com.quicktour.dto.Country;
import com.quicktour.entity.Company;
import com.quicktour.entity.User;
import com.quicktour.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Controller that handles all activity on main page
 */

@Controller
public class ApplicationController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UsersService usersService;
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
                           @PathVariable("page") int pageNum,
                           @RequestParam(value = "numberOfRecords", required = false) Integer numberOfRecordsPerPage) {
        addSidebarAttributes(map);
        Page page = toursService.findAllToursAndCalculateDiscount(pageNum, numberOfRecordsPerPage);
        map.addAttribute("page", page);
        return "index-tile";
    }


    /**
     * run when user select one of the country on sidebar
     * add filtered by country tours to the model of main page
     */
    @RequestMapping(value = "/country/{country}/{page}", method = RequestMethod.GET)
    public String indexCountry(@PathVariable("country") String country,
                               @PathVariable("page") int pageNum,
                               ModelMap map) {
        map.addAttribute("page", toursService.findToursByCountry(country, pageNum));
        addSidebarAttributes(map);
        return "index-tile";
    }

    /**
     * run when user select one of the place on sidebar
     * add filtered by place tours to the model of main page
     */
    @RequestMapping(value = "/place/{place}/{page}", method = RequestMethod.GET)
    public String indexPlace(@PathVariable("place") String place,
                             @PathVariable("page") int pageNum,
                             ModelMap map) {
        map.addAttribute("page", toursService.findToursByPlaces(place, pageNum));
        addSidebarAttributes(map);
        return "index-tile";
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
        map.addAttribute("page", toursService.findToursByPrice(min, max, pageNum));
        addSidebarAttributes(map);
        return "index-tile";
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
        if (pageNum == 0 && country.isEmpty() && place.isEmpty() &&
                minDate == null && maxDate == null &&
                minPrice == null && maxPrice == null) {
            return "redirect:/page/0";
        }
        map.addAttribute("page", toursService.extendFilter(country, place, minDate, maxDate,
                minPrice, maxPrice, pageNum));
        addSidebarAttributes(map);
        return "index-tile";
    }


    private void addSidebarAttributes(ModelMap map) {

        List<Country> countriesWithPlaces = placeService.findCountriesWithPlaces();
        map.addAttribute("countriesWithPlaces", countriesWithPlaces);
        map.addAttribute("places", placeService.extractPlaces(countriesWithPlaces));
        map.addAttribute("famousTours", toursService.findFamousTours());
        User currentUser = usersService.getCurrentUser();
        if (currentUser != null && currentUser.getCompanyCode() != null) {
            Company company = companyService.findByCompanyCode(currentUser.getCompanyCode());
            if (company != null) {
                map.addAttribute("companyName", company.getName());
            }
        } else {
            map.addAttribute("companyName", "");
        }

    }


}

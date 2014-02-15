package com.quicktour.controller;

import com.quicktour.entity.CompleteTourInfo;
import com.quicktour.entity.PriceDescription;
import com.quicktour.entity.Tour;
import com.quicktour.entity.TourInfo;
import com.quicktour.service.PriceIncludeService;
import com.quicktour.service.SqlDatePropertyEditor;
import com.quicktour.service.ToursManageService;
import com.quicktour.service.ToursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasRole('agent')")
@RequestMapping(value = "/agent")
public class ManageController {

    @Autowired
    private ToursManageService toursManageService;
    @Autowired
    private PriceIncludeService priceIncludeService;
    @Autowired
    private ToursService toursService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDatePropertyEditor());
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
    }

    @RequestMapping(value = "/manageTours")
    String manage(ModelMap map) {
        map.addAttribute("toursInfo", new CompleteTourInfo());
        List<PriceDescription> priceIncludes = priceIncludeService.findAll();
        map.addAttribute("prIncludes", priceIncludes);
        map.addAttribute("edit", false);
        return "manage-tours";
    }

    @RequestMapping(value = "/manageTours/{tourId}")
    String edit(ModelMap map,
                @PathVariable("tourId") int id) {
        Tour tour = toursService.findTourById(id);
        CompleteTourInfo completeTourInfo = new CompleteTourInfo();
        completeTourInfo.setTour(tour);
        completeTourInfo.setTourInfo((List<TourInfo>) tour.getTourInfo());
        completeTourInfo.setPlaces(tour.getToursPlaces());
        map.addAttribute("toursInfo", completeTourInfo);
        List<PriceDescription> priceIncludes = priceIncludeService.findAll();
        map.addAttribute("prIncludes", priceIncludes);
        map.addAttribute("edit", true);
        return "manage-tours";
    }

    @RequestMapping(value = {"/manageTours", "/manageTours/{tourId}"}, method = RequestMethod.POST)
    String processManege(CompleteTourInfo toursInfo,
                         @RequestParam(value = "mainPhoto", required = false) MultipartFile mainPhoto) {

        toursManageService.saveCombineTours(toursInfo, mainPhoto);

        return "redirect:/manageTours";
    }


    @RequestMapping(value = "/manageTours/deactivate", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Boolean> deactivateTour(@RequestParam("id") int id) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("status", toursManageService.changeActiveStage(id, false));
        return map;
    }

    @RequestMapping(value = "/manageTours/activate", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Boolean> activateTour(@RequestParam("id") int id) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("status", toursManageService.changeActiveStage(id, true));
        return map;
    }


    @RequestMapping(value = "/showOwnTours")
    String showAgentTours(ModelMap map) {
        map.addAttribute("tours", toursService.findAgencyTour());
        return "agentTours";
    }

    @RequestMapping(value = "/getAgencyToursWithoutDiscounts")
    @ResponseBody
    public Map<String, Object> getAgencyTours() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Tour> agencyTours = toursService.findAgencyToursWithEmptyDiscountPolicies();
        for (Tour tour : agencyTours) {
            map.put(String.valueOf(tour.getTourId()), tour.getName());
        }
        return map;
    }

}

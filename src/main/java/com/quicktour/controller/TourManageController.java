package com.quicktour.controller;

import com.quicktour.entity.PriceDescription;
import com.quicktour.entity.Tour;
import com.quicktour.service.PriceIncludeService;
import com.quicktour.service.SqlDatePropertyEditor;
import com.quicktour.service.ToursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasRole('agent')")
@RequestMapping("/tours")
public class TourManageController {

    private final Logger logger = LoggerFactory.getLogger(TourManageController.class);

    @Autowired
    private PriceIncludeService priceIncludeService;

    @Autowired
    private ToursService toursService;

    @Value("${maxImageSize}")
    int maxImageSize;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDatePropertyEditor());
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
    }

    @RequestMapping(method = RequestMethod.GET)
    String index(ModelMap map) {
        map.addAttribute("tours", toursService.findByCurrentUserAgency());
        return "agentTours";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    String manage(ModelMap map) {
        List<PriceDescription> priceIncludes = priceIncludeService.findAll();
        map.addAttribute("tour", new Tour());
        map.addAttribute("priceIncludes", priceIncludes);
        map.addAttribute("edit", false);
        return "manage-tours";
    }

    @RequestMapping(value = "/edit/{tourId}", method = RequestMethod.GET)
    String edit(ModelMap map,
                @PathVariable("tourId") int id) {
        Tour tour = toursService.findTourById(id);
        if (tour == null) {
            return "404";
        }
        logger.debug("Tour description {}", tour.getDescription().length());
        List<PriceDescription> priceIncludes = priceIncludeService.findAll();
        map.addAttribute("tour", tour);
        map.addAttribute("priceIncludes", priceIncludes);
        map.addAttribute("edit", true);

        return "manage-tours";
    }


    @RequestMapping(value = {"/add", "/edit/{tourId}"}, method = RequestMethod.POST)
    String processManege(@Valid Tour tour, BindingResult bindingResult,
                         @RequestParam(value = "mainPhoto", required = false) MultipartFile image) {
        String type = image.getContentType().split("/")[0];
        if (!image.isEmpty() && !type.equalsIgnoreCase("image")) {
            bindingResult.rejectValue("photo", "photo.type", "Uploaded file is not an image");
        }
        if (image.getSize() > maxImageSize) {
            bindingResult.rejectValue("photo", "photo.invalid", "Maximum upload size of " + maxImageSize + " bytes exceeded ");
        }
        logger.debug("BindingResult :{}", bindingResult);
        if (bindingResult.hasErrors()) {
            return "manage-tours";
        }

        toursService.saveCombinedTours(tour, image);

        return "redirect:/tours";
    }


    @RequestMapping(value = "/toggleActive", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Boolean> toggleActiveInTour(@RequestParam("id") int id) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        toursService.toogleActive(id);
        map.put("status", true);
        return map;
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

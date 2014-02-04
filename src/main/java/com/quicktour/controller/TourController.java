package com.quicktour.controller;

import com.quicktour.entity.Comment;
import com.quicktour.entity.Place;
import com.quicktour.entity.Tour;
import com.quicktour.entity.User;
import com.quicktour.service.CommentService;
import com.quicktour.service.ToursService;
import com.quicktour.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    final Logger logger = LoggerFactory.getLogger(TourController.class);
    @Autowired
    private ToursService toursService;
    @Autowired
    private CommentService commentService;
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

        Tour tour = toursService.findTourByIdWithPlacesAndPriceIncludes(id);
        String description = toursService.cutDescription(tour.getDescription());
        BigDecimal minPrice = toursService.findMinPrice(tour);
        double maxDiscount = toursService.findMaxDiscount(tour);

        map.addAttribute("tour", tour);
        map.addAttribute("minPrice", minPrice);
        map.addAttribute("maxDiscount", maxDiscount);
        map.addAttribute("description", description);
        map.addAttribute("user", usersService.getCurrentUser());


        return "place-extended";
    }

    /**
     * Method gets information about necessary comments for the page
     * on which user is situated
     */
    @RequestMapping(value = "/{id}/getComments", method = RequestMethod.POST)
    @ResponseBody
    public Page getComments(
            @PathVariable("id") int tourId,
            @RequestParam("page") int page,
            @RequestParam("numberOfRecords") int numberOfRecordsPerPage) {
        return commentService.findAllComments(tourId, page, numberOfRecordsPerPage);
    }

    @RequestMapping(value = "/{id}/removeComment", method = RequestMethod.POST)
    @ResponseBody
    public String removeComment(
            Comment comment
    ) {
        User commentUser = commentService.findOne(comment.getCommentId()).getUser();
        if (!commentUser.getLogin().equals(usersService.getCurrentUser().getLogin())) {
            return "Error";
        }
        commentService.delete(comment);
        return "Ok";
    }

    @RequestMapping(value = "/{id}/getLastComments", method = RequestMethod.POST)
    @ResponseBody
    public Page getLastComments(
            @PathVariable("id") int tourId,
            @RequestParam(value = "numberOfRecords") int numberOfRecordsPerPage) {
        return commentService.findLastPageComments(tourId, numberOfRecordsPerPage);
    }

    @RequestMapping(value = "/{tourId}/saveComment", method = RequestMethod.POST)
    @ResponseBody
    public Comment saveComment(Comment comment, @PathVariable("tourId") int tourId) {
        Tour tour = new Tour();
        tour.setTourId(tourId);
        comment.setTour(tour);
        logger.debug("Comment {}", comment);
        if (!"".equals(comment.getContent())) {
            comment = commentService.saveComment(comment);
        }
        return comment;
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

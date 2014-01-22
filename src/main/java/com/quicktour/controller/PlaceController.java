package com.quicktour.controller;

import com.quicktour.entity.Comment;
import com.quicktour.entity.Place;
import com.quicktour.entity.Tour;
import com.quicktour.service.CommentService;

import com.quicktour.service.OrdersService;
import com.quicktour.service.ToursService;
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
@Controller
public class PlaceController {

    @Autowired
    private ToursService toursService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrdersService ordersService;

    final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    /**
     * Method get information about current tour, it's min price,
     * max discount and users star rating from database and show it to user
     * @param id - identificator of the tour which user want to review
     * @param map - model that will be mapped to the place-extended tile
     * @return view place-extended
     */
    @RequestMapping(value = "/tour/{id}", method = RequestMethod.GET)
    public String getTours(@PathVariable("id") int id, ModelMap map) {

        Tour tour = toursService.findTourById(id);
        String description = toursService.cutDescription(tour.getDescription());
        BigDecimal minPrice = toursService.findMinPrice(tour);
        double maxDiscount = toursService.findMaxDiscount(tour);

        map.addAttribute("tour", tour);
        map.addAttribute("minPrice", minPrice);
        map.addAttribute("maxDiscount", maxDiscount);
        map.addAttribute("description", description);
        map.addAttribute("ordersService", ordersService);

        return "place-extended";
    }

    /**
     * Method gets information about necessary comments for the page
     * on which user is situated
     * @param map - model which will be mapped to the comments page
     * @param id  - id of the tour to which the comments belong
     * @param page - necessary page(on which user was click)
     * @return part of the page(comments.jsp) which will be processed on the client side
     */
    @RequestMapping(value = "/tour/{id}/getComments", method = RequestMethod.POST)
    public String getComments(ModelMap map,
                              @RequestParam(value = "id") int id,
                              @RequestParam(value = "page") int page) {
        Tour tour = toursService.findTourById(id);
        Page pageOfComments = commentService.findAllComments(id, page);

        map.addAttribute("tour", tour);
        map.addAttribute("page", pageOfComments);
        map.addAttribute("ordersService", ordersService);

        return "comments";
    }

    /**
     * Method get information about comments from database and show it to user
     * @param map - model which will be mapped to the comments page
     * @param tourId - id of the tour to which the comments belong
     * @param comment - comment which will be stored
     * @return part of the page which will be processed on the client side
     */
    @RequestMapping(value = "/tour/{id}/saveComment", method = RequestMethod.POST)
    public String saveComment(ModelMap map, @RequestParam (value = "tourId") int tourId,
                              @RequestParam (value ="comment") String comment) {
        if(!(comment.equals(""))){
            commentService.saveComment(comment,tourId);
        }
        Tour tour = toursService.findTourById(tourId);
        Page pageOfComments = commentService.findLastPageComments(tourId);

        map.addAttribute("tour", tour);
        map.addAttribute("page", pageOfComments);
        map.addAttribute("ordersService", ordersService);

        return "comments";
    }

    /**
     * Method get information about comments from database and show it to user
     * @param map - model which will be mapped to the comments page
     * @param tourId - id of the tour to which the comments belong
     * @param commentId - id of the comments which was edited
     * @param comment - comment which will be stored
     * @return part of the page which will be processed on the client side
     */
    @RequestMapping(value = "/tour/{id}/editComment", method = RequestMethod.POST)
    public String editComment(ModelMap map, @RequestParam (value = "tourId") int tourId,
                              @RequestParam("commentId") int commentId,
                              @RequestParam ("comment") String comment) {

        if(!"".equals(comment)){
            commentService.editComment(comment, commentId, tourId);
        }
        Tour tour = toursService.findTourById(tourId);
        Page pageOfComments = commentService.findPageByCommentId(commentId, tourId);

        map.addAttribute("tour", tour);
        map.addAttribute("page", pageOfComments);
        map.addAttribute("ordersService", ordersService);

        return "comments";
    }

    /**
     * Methods gets the required information for the components on tour page and
     * puts them on the container that processed on the client side
     * @param id - identificator of the tour infomation about we are interested in
     * @return map (which will be convert to JSON object) with all required information
     * for GoogleMap and PanoramioWidget
     */
    @RequestMapping(value = "/tour/{id}/getData", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getData(@RequestParam("id") int id) {

        Tour tour = toursService.findTourById(id);
        List<Place> toursPlaces = tour.getToursPlaces();
        List<Comment> comments = commentService.findCommentsByTour(tour);
        Map<String, Object> map = new HashMap<>();
        map.put("places", toursPlaces);
        map.put("travelType", tour.getTravelType());
        map.put("comments", comments);

        return map;
    }



}

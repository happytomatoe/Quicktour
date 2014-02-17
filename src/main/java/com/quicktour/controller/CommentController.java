package com.quicktour.controller;

import com.quicktour.entity.Comment;
import com.quicktour.entity.Tour;
import com.quicktour.entity.User;
import com.quicktour.service.CommentService;
import com.quicktour.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Manages comments
 *
 * @author Roman Lukash
 */
@Controller
@RequestMapping("/tour")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private UsersService usersService;

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

}

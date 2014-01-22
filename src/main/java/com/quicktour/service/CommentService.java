package com.quicktour.service;

import com.quicktour.entity.*;
import com.quicktour.repository.CommentRepository;
import com.quicktour.repository.ToursRepository;
import com.quicktour.repository.UserRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;
import java.util.*;

/**
 * Service which processed required information for the comments on tour page
 *
 * @author Kolya Yanchiy
 */
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ToursRepository toursRepository;

    @Autowired
    UserRepository userRepository;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UsersService.class);

    //The constant which show number of comments on every page
    private static final int NUMBER_OF_RECORDS_PER_PAGE = 3;

    /**
     * Method gets all comments by tour
     * @param tour - tour which comment we are interested in
     * @return all comments for the tour
     */
    public List<Comment> findCommentsByTour(Tour tour) {
        List<Comment> comments = commentRepository.findByTour(tour);
        return comments;
    }

    /**
     * Method returns page of comments we are intersted in
     * @param id - identificator of the tour which page we are interested in
     * @param pageNumber - number of required page
     * @return necessary page with comments
     */
    public Page<Comment> findAllComments(int id, int pageNumber) {
        PageRequest pageRequest=new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE);
        return commentRepository.findCommentsByTour(toursRepository.findByTourId(id), pageRequest);
    }

    /**
     * Method find page of comments by the identificator of comments
     * @param commentId - id of the comment which page we are interested in
     * @param tourId - id of the tour in which comment we are intersted in
     * @return necessary page with comments
     */
    public Page<Comment> findPageByCommentId(int commentId, int tourId){

        Tour tour = toursRepository.findByTourId(tourId);
        List<Comment> comments = commentRepository.findByTour(tour);
        int k=0;
        int pages =(int) Math.ceil(comments.size()/NUMBER_OF_RECORDS_PER_PAGE);
        int findedPage=pages;
        for (int i=0; i<pages; i++){
            for (int j = k; j<k+NUMBER_OF_RECORDS_PER_PAGE; j++){
                Comment comment = comments.get(j);
                if(commentId == comment.getCommentId()) {
                    findedPage=i;
                }
            }
            k+=NUMBER_OF_RECORDS_PER_PAGE;
        }
        logger.info("Page request: "+ findedPage + "for tour" + tour.getName());
        PageRequest pageRequest = new PageRequest(findedPage, NUMBER_OF_RECORDS_PER_PAGE);
        return commentRepository.findCommentsByTour(toursRepository.findByTourId(tourId), pageRequest);
    }

    /**
     * Method find and return last page with comments for current tour
     * @param id - id of the tour which comments we are interested in
     * @return last page with comments for current tour
     */
    public Page<Comment> findLastPageComments(int id) {

        PageRequest pageRequest=new PageRequest(0, NUMBER_OF_RECORDS_PER_PAGE);
        Page<Comment> comments= commentRepository.findCommentsByTour(toursRepository.findByTourId(id), pageRequest);
        int lastPage = comments.getTotalPages()-1;

        PageRequest necessaryPage=new PageRequest(lastPage, NUMBER_OF_RECORDS_PER_PAGE);
        Page<Comment> necessaryComments= commentRepository.findCommentsByTour(toursRepository.findByTourId(id), necessaryPage);

        return necessaryComments;
    }

    /**
     * Method prepares object for saving in database set it's user name, text of comment
     * tour and check if current comment is valid(on HTML tags)
     * @param commentText - text of input comment
     * @param tourId - id of the current tour
     */
    public void saveComment(String commentText, int tourId) {

        Comment comment = new Comment();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String commentUpdated =  commentText.replace("\n", "<br/>");
        String commentTextForSave= Jsoup.clean(commentUpdated, Whitelist.basic());
        if (commentTextForSave.isEmpty()){
        } else {
            comment.setComment(commentTextForSave);
            comment.setUser(userRepository.findByLogin(userName));
            comment.setTour(toursRepository.findByTourId(tourId));
            commentRepository.saveAndFlush(comment);
            logger.info("The user"+ comment.getUser().getLogin()+" was add new comment:"+comment.getComment());
        }
    }

    /**
     * Method prepares object for editing in database set it's user name, text of comment
     * tour and check if current comment is valid(on HTML tags)
     * @param commentText - text of input comment
     * @param commentId - id of comments which will be edited
     * @param tourId - id of the current tour
     */

    public void editComment(String commentText, int commentId, int tourId){

        Comment comment = new Comment();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        String commentUpdated = commentText.replace("\n","<br/>");
        String commentTextForSave= Jsoup.clean(commentUpdated, Whitelist.basic());
        if (commentTextForSave.isEmpty()){
        } else {
            comment.setCommentId(commentId);
            comment.setComment(commentTextForSave);
            comment.setUser(userRepository.findByLogin(userName));
            comment.setTour(toursRepository.findByTourId(tourId));
            commentRepository.saveAndFlush(comment);
            logger.info("The user"+ comment.getUser().getLogin()+" was edit comment: "+comment.getCommentId()+" "+comment.getComment());
        }
    }

}

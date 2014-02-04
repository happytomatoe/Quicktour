package com.quicktour.service;

import com.quicktour.entity.Comment;
import com.quicktour.entity.Tour;
import com.quicktour.entity.User;
import com.quicktour.repository.CommentRepository;
import com.quicktour.repository.ToursRepository;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service which processed required information for the comments on tour page
 *
 * @author Kolya Yanchiy
 */
@Service
@Transactional
public class CommentService {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UsersService.class);
    final TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("org/kefirsf/bb/safehtml.xml");
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ToursRepository toursRepository;
    @Autowired
    UsersService usersService;

    /**
     * Method returns page of comments we are intersted in
     *
     * @param pageNumber - number of required page
     * @return necessary page with comments
     */
    public Page<Comment> findAllComments(int tourId, int pageNumber, int numberOfRecordsPerPage) {
        PageRequest pageRequest = new PageRequest(pageNumber, numberOfRecordsPerPage);
        Tour tour = new Tour();
        tour.setTourId(tourId);
        return commentRepository.findCommentsByTourAndParentIsNull(tour, pageRequest);
    }

    /**
     * Method find and return last page with comments for current tour
     *
     * @return last page with comments for current tour
     */
    public Page<Comment> findLastPageComments(int tourId, int numberOfRecordsPerPage) {
        int size = commentRepository.findCountByTourId(tourId).intValue();
        double divider = size % numberOfRecordsPerPage;
        int page = divider == 0 ? size / numberOfRecordsPerPage - 1 : size / numberOfRecordsPerPage;
        logger.debug("Size {}.\nDivider {}.\nPage {}", size, divider, page);
        return findAllComments(tourId, page, numberOfRecordsPerPage);
    }

    public Comment findOne(Integer id) {
        return commentRepository.findOne(id);
    }

    /**
     * Method prepares object for saving in database set it's user name, text of comment
     * tour and check if current comment is valid(on HTML tags)
     */
    public Comment saveComment(Comment comment) {
        User currentUser = usersService.getCurrentUser();
        String commentText = comment.getContent();
        String commentUpdated = commentText.replace("\n", "<br/>");
        String commentTextForSave = processor.process(commentUpdated);
        if (commentTextForSave.isEmpty()) {
        } else {
            comment.setContent(commentTextForSave);
            comment.setUser(currentUser);
            logger.info("The user {}  was add new comment: {}", comment.getUser().getLogin(), comment.getContent());
            comment = commentRepository.saveAndFlush(comment);
        }
        return comment;
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}

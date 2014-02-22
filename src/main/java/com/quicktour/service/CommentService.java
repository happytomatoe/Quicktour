package com.quicktour.service;

import com.quicktour.entity.Comment;
import com.quicktour.entity.Role;
import com.quicktour.entity.Tour;
import com.quicktour.entity.User;
import com.quicktour.repository.CommentRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
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
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UsersService usersService;
    private static final TextProcessor processor = BBProcessorFactory.getInstance().create();

    public Page<Comment> findAllComments(int tourId, int pageNumber, int numberOfRecordsPerPage) {
        PageRequest pageRequest = new PageRequest(pageNumber, numberOfRecordsPerPage);
        Tour tour = new Tour();
        tour.setTourId(tourId);
        return commentRepository.findByTourAndParentIsNull(tour, pageRequest);
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
        int commentId = comment.getCommentId();
        Comment originalComment = null;
        if (commentId != 0) {
            originalComment = findOne(commentId);
            if (!originalComment.getUser().getName().equals(currentUser.getUsername()) &&
                    currentUser.getRole().getRoleId() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You don't have right to edit this comment");
            }
        }
        String commentText = comment.getContent();
        commentText = Jsoup.clean(commentText, Whitelist.basic());
        commentText = processor.process(commentText);
        if (commentText.isEmpty()) {
            throw new IllegalArgumentException("Comment is empty when saving comment");
        } else {
            comment.setContent(commentText);
            if (originalComment != null && currentUser.getRole().getRoleId() == Role.ROLE_ADMIN) {
                comment.setUser(originalComment.getUser());
            } else {
                comment.setUser(currentUser);
            }
            logger.info("The user {}  was add new comment: {}", comment.getUser().getUsername(), comment.getContent());
            comment = commentRepository.saveAndFlush(comment);
        }
        return comment;
    }

    public String delete(Comment comment) {
        User currentUser = usersService.getCurrentUser();
        Comment originalComment = findOne(comment.getCommentId());
        if (originalComment.getUser().getUserId() != currentUser.getUserId() &&
                currentUser.getRole().getRoleId() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("You don't have right to delete this comment");
        }

        commentRepository.delete(comment);
        return "Ok";
    }

}

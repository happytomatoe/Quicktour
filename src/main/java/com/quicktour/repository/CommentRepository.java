package com.quicktour.repository;

import com.quicktour.entity.Comment;
import com.quicktour.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findByTourAndParentIsNull(Tour tour, Pageable pageable);

    @Query("SELECT COUNT(c.commentId) FROM Comment c WHERE c.tour.tourId=?1 AND c.parent IS NULL")
    Long findCountByTourId(int tourId);

}

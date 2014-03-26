package com.quicktour.tests;

import com.quicktour.entity.Tour;
import com.quicktour.repository.CommentRepository;
import com.quicktour.service.CommentService;
import com.quicktour.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import static org.mockito.Mockito.*;
/**
 * Created by rluk on 3/25/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTests {
    @InjectMocks
    @Spy
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UsersService usersService;

    @Test
    public void testFindLastPageComments1() throws Exception {
        //Given
        int tourId=1;
        int expectedPage = 3;
        long tourSize = 10L;
        Tour tour = new Tour();
        tour.setTourId(tourId);
        int numberOfRecordsPerPage = 3;
        //When
        when(commentRepository.findCountByTourId(anyInt())).thenReturn(tourSize);
        commentService.findLastPageComments(tourId, numberOfRecordsPerPage);
        //then
        verify(commentRepository).findByTourAndParentIsNull(any(Tour.class),
                eq(new PageRequest(expectedPage, numberOfRecordsPerPage)));
    }

    @Test
    public void testSaveComment() throws Exception {

    }
}

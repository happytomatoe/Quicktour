package com.quicktour.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by rluk on 3/26/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoExamples {
    @Mock
    Map<String, String> map;

    @Test
    public void testBasicMockitoBehavior() {
        //when
        map.put("Martin", "380989209099");
        map.clear();
        //then
        verify(map).put(anyString(), anyString());
        verify(map).clear();

    }

    @Test
    public void testStubBehavior() throws Exception {
        String returnString = "HolyChick!";
        String expectedString = "HolyChick!";
        //call
        when(map.put(anyString(), anyString())).thenReturn(returnString);
        //verify
        assertEquals(expectedString, map.put("", ""));
    }
    @Test
    public void testArgumentMachers(){


    }


}

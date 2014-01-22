package com.quicktour.service.AsyncService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {

    @Scheduled(fixedRate = 3000)
    public void testScheduling(){

      /*  System.out.println("running every 3 seconds");*/
    }


    @Scheduled(fixedRate = 2000)
    public void testScheduling2(){

        /*System.out.println("running every 2 seconds");*/
    }


}



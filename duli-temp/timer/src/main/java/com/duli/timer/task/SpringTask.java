package com.duli.timer.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SpringTask {

    @Scheduled(cron = "0/2 * * * * ?")
    public void run(){
        System.out.println(Thread.currentThread().getName()+new Date());
    }




}

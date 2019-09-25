package com.duli.timer.jdk;

import java.util.Date;
import java.util.TimerTask;
public class MyTimerTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("这是定时器...");
        System.out.println("date--->"+new Date());
    }
}

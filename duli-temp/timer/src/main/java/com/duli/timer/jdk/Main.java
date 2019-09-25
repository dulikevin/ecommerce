package com.duli.timer.jdk;

import java.util.Date;
import java.util.Timer;

public class Main {
    public static void main(String args[]){
        Timer time = new Timer();
        MyTimerTask task = new MyTimerTask();
        System.out.println(new Date());
        //延时三秒执行任务
        //time.schedule(task,5000);
        //三秒后，每个几秒执行任务
        time.schedule(task,2000,3000);
    }
}

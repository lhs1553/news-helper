package com.ckpoint.news;

import org.junit.Test;

import java.util.Calendar;

public class TimeTest {

    @Test
    public void checkHourTest() {

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.print("dayofweek = " + calendar.get(Calendar.DAY_OF_WEEK));
    }
}

package com.x404.beat.utils;


import java.util.Date;

/**
 * Created by chaox on 4/26/2017.
 */
public class TimeUtils
{

    public static final int SOCKET_TIMEOUT = 5000;

    public static Date toMinute(Date start_time) {
        return toMinute(start_time.getTime());
    }

    private static Date toMinute(long time) {
        time = ((long) (time / (5 * 60000D) + 0.5)) * 5 * 60000;
        return new Date(time);
    }

}

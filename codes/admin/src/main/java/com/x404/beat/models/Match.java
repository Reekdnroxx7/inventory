package com.x404.beat.models;

import com.x404.beat.utils.Platform;
import com.x404.beat.utils.TimeUtils;

import java.util.Date;

/**
 * Created by chaox on 4/26/2017.
 */
public class Match
{

    protected Platform platform;

    protected String home;

    protected String away;

    protected Date startTime;

    protected String matchKey;

    protected String uniqueKey;

    protected String stdHome;

    protected String stdAway;
    private String id;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getMatchKey() {
        if( matchKey == null ) {
            matchKey = getHome() + "_" + getAway() + "_" + TimeUtils.toMinute(startTime).getTime();
        }
        return matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }



    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String key) {
        this.uniqueKey = key;
    }


    public String getStdHome() {
        return stdHome;
    }

    public void setStdHome(String stdHome) {
        this.stdHome = stdHome;
    }

    public String getStdAway() {
        return stdAway;
    }

    public void setStdAway(String stdAway) {
        this.stdAway = stdAway;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

package com.x404.beat.models;

import com.x404.beat.utils.Platform;

import java.util.Date;

/**
 * Created by chaox on 5/1/2017.
 */
public class Spread
{

    protected Platform platform;
    /***/
    protected Double hdp;
    /**
     * 主队赔率
     */
    protected Double homeOdds;
    /***/
    protected Double awayOdds;
    /***/
    protected Double max_money;

    protected Match match;

    protected Double min_money;

    protected Date initTime;

    protected String logId;
    protected String uniqueKey;
    private String id;


    public Double getHdp() {
        return hdp;
    }


    public void setHdp(Double hdp) {
        this.hdp = hdp;
    }



    public Double getHomeOdds() {
        return homeOdds;
    }


    public void setHomeOdds(Double homeOdds) {
        this.homeOdds = homeOdds;
    }


    public Double getAwayOdds() {
        return awayOdds;
    }


    public void setAwayOdds(Double awayOdds) {
        this.awayOdds = awayOdds;
    }

    public Double getMax_money() {
        return max_money;
    }

    public void setMax_money(Double max_money) {
        this.max_money = max_money;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }


    public Double getMin_money() {
        return min_money;
    }

    public void setMin_money(Double min_money) {
        this.min_money = min_money;
    }


    public Date getInitTime() {
        return initTime;
    }


    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }


    public String getUniqueKey() {
      return this.uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }


    public String getLogId() {
        return this.logId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }


    public Object clone() {
        try {
            return super.clone();
        } catch( CloneNotSupportedException e ) {
            throw new RuntimeException(e);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

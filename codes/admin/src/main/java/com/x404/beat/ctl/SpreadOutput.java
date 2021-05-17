package com.x404.beat.ctl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ISO8601Utils;
import com.x404.beat.core.util.DateTimeUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;

/**
 * Created by chaox on 12/12/2017.
 */
public class SpreadOutput
{
    public final String id;
    public final Double homeOdds;
    public final Double awayOdds;
    public final String platform;
    public final Date initTime;
    public final boolean deleted;



    public SpreadOutput(JsonNode spread) {
        this.id = spread.path("uniqueKey").asText();
        this.homeOdds = spread.path("homeOdds").asDouble();// spread.getHomeOdds();
        this.awayOdds = spread.path("awayOdds").asDouble();// spread.getAwayOdds();
        this.platform = spread.path("platform").asText();// spread.getPlatform();


        try {
            this.initTime = ISO8601Utils.parse(spread.path("initTime").path("$date").asText(),new ParsePosition(0));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.deleted = spread.path("deleted").asBoolean(); //spread.getDeleted();
    }
}

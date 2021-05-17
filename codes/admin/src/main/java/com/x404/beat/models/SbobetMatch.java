package com.x404.beat.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.x404.beat.models.Match;
import org.springframework.data.annotation.Transient;

import java.util.List;


public class SbobetMatch extends Match
{

    private Long sport_id;
    private Long league_id;
    private Long event_id;
    private String status;
    private String day;

    public Long getSport_id() {
        return sport_id;
    }

    public void setSport_id(Long sport_id) {
        this.sport_id = sport_id;
    }

    public Long getLeague_id() {
        return league_id;
    }

    public void setLeague_id(Long league_id) {
        this.league_id = league_id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @JsonIgnore
    @Transient
    private List<SbobetSpread> sbobetSpreads;

    public List<SbobetSpread> getSbobetSpreads() {
        return sbobetSpreads;
    }

    public void setSbobetSpreads(List<SbobetSpread> sbobetSpreads) {
        this.sbobetSpreads = sbobetSpreads;
    }
}

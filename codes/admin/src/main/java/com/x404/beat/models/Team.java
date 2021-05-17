package com.x404.beat.models;


import com.xc350.web.base.model.IdEntity;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Team implements IdEntity
{
    @Id
    private String name;


//    private Long leagueId;


    public Team() {
    }

    public Team(String name) {
        this.name = name;
//        this.leagueId = leagueId;
    }

    private Map<String, String> translations;

    @Override
    public Serializable getId() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Map<String, String> getTranslations() {
        if( translations == null ) {
            translations = new HashMap<>();
        }
        return translations;
    }

    public void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }

    public void addTranslation(String locale, String value) {
        getTranslations().put(locale, value);
    }

//    public Long getLeagueId() {
//        return leagueId;
//    }
//
//    public void setLeagueId(Long leagueId) {
//        this.leagueId = leagueId;
//    }
}

package com.x404.beat.models;


import java.util.Objects;

/**
 * Created by chaox on 8/3/2017.
 */
public class League implements com.xc350.web.base.model.IdEntity {
    private String id;
    private String league_name;
    private String league_cn_name;
    private String regionId;
    private String region_name;
    private String region_cn_name;
    private Boolean disable ;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getLeague_cn_name() {
        return league_cn_name;
    }

    public void setLeague_cn_name(String league_cn_name) {
        this.league_cn_name = league_cn_name;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_cn_name() {
        return region_cn_name;
    }

    public void setRegion_cn_name(String region_cn_name) {
        this.region_cn_name = region_cn_name;
    }

    public boolean isDisable() {
        return Objects.equals(true,disable);
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}

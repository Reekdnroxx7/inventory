package com.x404.beat.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SbobetSpread extends Spread implements Cloneable, Serializable
{

    private Long line_id;
    private Long type;


    public Long getLine_id() {
        return line_id;
    }

    public void setLine_id(Long line_id) {
        this.line_id = line_id;
    }


    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getType() {
        return type;
    }
}

package com.x404.beat.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xc350.web.base.model.IdEntity;

import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "clazz")
public interface IMatch extends IdEntity
{

    public String getHome();

    public void setHome(String home);

    public String getAway();

    public void setAway(String away);

    public Date getStartTime();

    public void setStartTime(Date startTime);

    public String getMatchKey();

    public void setMatchKey(String matchKey);

    public String getStdHome();

    public String getStdAway();

    public Date getStdStartTime();

    public String getUniqueKey();

    public void setUniqueKey(String key);


}

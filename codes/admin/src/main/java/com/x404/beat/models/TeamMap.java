package com.x404.beat.models;

import com.xc350.web.base.model.IdEntity;
import org.springframework.data.annotation.Id;

/**
 * Created by chaox on 4/28/2017.
 */
public class TeamMap implements IdEntity
{

    @Id
    protected String src_name;
    protected String std_name;

    public TeamMap() {
    }

    @Override
    public String getId() {
        return src_name;
    }

    public TeamMap(String src_name, String std_name) {
        this.src_name = src_name;
        this.std_name = std_name;
    }


    public void setSrc_name(String src_name) {
        this.src_name = src_name;
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public String getSrc_name() {
        return src_name;
    }
}

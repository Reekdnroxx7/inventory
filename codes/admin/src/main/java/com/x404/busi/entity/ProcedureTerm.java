package com.x404.busi.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Created by chaox on 1/17/2018.
 */
public class ProcedureTerm
{
    private String id;
    private String procedureCode;
    private String materialCode;
    private int quantity;
    private String unit;
    /**创建时间*/
    @CreatedDate
    private java.util.Date create_date;
    /**创建人*/
    @CreatedBy
    private String create_by;
    /***/
    @LastModifiedDate
    private java.util.Date update_date;
    /***/
    @LastModifiedBy
    private String update_by;
    /**备注*/
    private String remarks;
}

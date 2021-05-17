//package com.x404.beat.models;
//
//import com.x404.beat.order.SpreadBuyer;
//import com.x404.module.entity.IdEntity;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import java.util.Date;
//
///**
// * Created by Administrator on 2017/12/9.
// */
//public class SpreadsOrder implements IdEntity
//{
//    private String id;
//    private SpreadBuyer.Order first;
////    private SpreadBuyer.Order second;
//    private int status;
//    private double totalMoney;
//    private ISpread initSpread;
//    private SpreadBuyer.Order fix;
//    private boolean pauseErrorHandler =false;
//
//    @CreatedDate
//    private Date createTime;
//
//    @LastModifiedDate
//    private Date updateTime;
//    public SpreadBuyer.Order getFirst() {
//        return first;
//    }
//
//    public void setFirst(SpreadBuyer.Order first) {
//        this.first = first;
//    }
//
//    public SpreadBuyer.Order getSecond() {
//        return second;
//    }
//
//    public void setSecond(SpreadBuyer.Order second) {
//        this.second = second;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public double getTotalMoney() {
//        return totalMoney;
//    }
//
//    public void setTotalMoney(double totalMoney) {
//        this.totalMoney = totalMoney;
//    }
//
//    public ISpread getInitSpread() {
//        return initSpread;
//    }
//
//    public void setInitSpread(ISpread initSpread) {
//        this.initSpread = initSpread;
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setFix(SpreadBuyer.Order fix) {
//        this.fix = fix;
//    }
//
//    public SpreadBuyer.Order getFix() {
//        return fix;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public boolean isPauseErrorHandler() {
//        return pauseErrorHandler;
//    }
//
//    public void setPauseErrorHandler(boolean pauseErrorHandler) {
//        this.pauseErrorHandler = pauseErrorHandler;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//}

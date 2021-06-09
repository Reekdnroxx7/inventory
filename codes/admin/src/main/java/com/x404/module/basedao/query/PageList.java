package com.x404.module.basedao.query;

import java.util.List;

/**
 * 类描述：分页查询结果封装类
 * 张代浩
 *
 * @version 1.0
 *          //
 * @date： 日期：2012-12-7 时间：上午10:20:04
 */

public class PageList<T> {


    private int start;
    private int limit = -1;
    /**
     * 如果totalCount !=-1 则表示使用传入的totalCount
     */
    private int totalCount = -1;
    private List<T> resultList = null;//结果集

    public PageList() {

    }


    public PageList(List<T> resultList) {
        super();
        this.resultList = resultList;
        this.totalCount = resultList.size();
    }

    public PageList(List<T> resultList, int totalCount) {
        super();
        this.resultList = resultList;
        this.totalCount = totalCount;
    }


    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return -1 不分页
     */
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


}

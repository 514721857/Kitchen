package com.ymt.sgr.kitchen.model;

import java.util.List;

/**
 * Created by 沈国荣 on 2018/6/21.
 * QQ:514721857
 * Description:
 */

public class OrderRespons {
    List<OrderBean> data;
    int   currPage;         // 当前页
    int   pageSize;       // 页面显示数据量
    int   totalPage;       // 总页数
    int   totalElements;  // 总数据量

    public  List<OrderBean>  getData() {
        return data;
    }

    public void setData( List<OrderBean>  data) {
        this.data = data;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}

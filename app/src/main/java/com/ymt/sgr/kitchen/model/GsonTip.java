package com.ymt.sgr.kitchen.model;

/**
 * Data：2018/11/27/027-16:49
 * By  沈国荣
 * Description:推送过来的json数据
 */
public class GsonTip {
    String orderId;
    int type;
    String shopId;
    int status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

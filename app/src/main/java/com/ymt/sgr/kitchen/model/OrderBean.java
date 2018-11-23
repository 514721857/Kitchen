package com.ymt.sgr.kitchen.model;

/**
 * Created by 沈国荣 on 2018/6/19.
 * QQ:514721857
 * Description:
 */

public class OrderBean {

    String buyerId;//买家主键，作关联
    String buyerOpenid;//来自微信
    int kfId;//客服id
    int shopId;//店铺id
    int type;//0到店自取，1表示外卖送
    int cfId;//厨房id
    int todaySeq;//当天序号，表示今天的第几单
    int psId;//配送员id
    String psStartTime;//配送开始时间
    String psEndTime;//：配送结束时间
    String psTimeLen;//配送=结束时间-开始时间，分钟

    String cfTimeLen;//：厨房订单完成时长，单位：分钟
    String cfEndTime;//：厨房完成制作的结束时间
    String cfStartTime;//：厨房开始制作的开始时间

    String orderId;//主键
    String username;//买家
    String summary;//摘要，如备注是否加辣
    String  address;//收货地址
    String total;//实收费用
     int status;//0新订单/待付款；
            /*1客服设置为确认付款/待制作；
            2厨房开始制作/制作中；
            3厨房完成制作/待配送(type=0时显示待领取)
4配送员开始配送/配送中(type=0时没有这步)
5配送员完成配送/订单完成；
            -1取消；*/
    String detail;//清单
    String expressFee;//配送费
    String amount;//餐费
    String quantity;//购买数量，一共买了多少份
    String sn;//订单号
    String phone;//联系方式
    String gmtCreate;//下单时间
    String sendTime;//配送时间，默认为当前立即配送

    public int getKfId() {
        return kfId;
    }

    public void setKfId(int kfId) {
        this.kfId = kfId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCfId() {
        return cfId;
    }

    public void setCfId(int cfId) {
        this.cfId = cfId;
    }

    public int getTodaySeq() {
        return todaySeq;
    }

    public void setTodaySeq(int todaySeq) {
        this.todaySeq = todaySeq;
    }

    public int getPsId() {
        return psId;
    }

    public void setPsId(int psId) {
        this.psId = psId;
    }

    public String getPsStartTime() {
        return psStartTime;
    }

    public void setPsStartTime(String psStartTime) {
        this.psStartTime = psStartTime;
    }

    public String getPsEndTime() {
        return psEndTime;
    }

    public void setPsEndTime(String psEndTime) {
        this.psEndTime = psEndTime;
    }

    public String getPsTimeLen() {
        return psTimeLen;
    }

    public void setPsTimeLen(String psTimeLen) {
        this.psTimeLen = psTimeLen;
    }

    public String getCfTimeLen() {
        return cfTimeLen;
    }

    public void setCfTimeLen(String cfTimeLen) {
        this.cfTimeLen = cfTimeLen;
    }

    public String getCfEndTime() {
        return cfEndTime;
    }

    public void setCfEndTime(String cfEndTime) {
        this.cfEndTime = cfEndTime;
    }

    public String getCfStartTime() {
        return cfStartTime;
    }

    public void setCfStartTime(String cfStartTime) {
        this.cfStartTime = cfStartTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(String expressFee) {
        this.expressFee = expressFee;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}

package com.ymt.sgr.kitchen.model;

/**
 * Data：2018/6/20/020-9:56
 * By  沈国荣
 * Description:
 */
public class User {
    String username;
    String password;
    String shopId;
    String enabled;
    String role;
    String token;
    String sellerId;
  /*  role: 0分店管理员，1厨房，2快递，99总管理账号，98客服
username：账号
phone：电话号码
shopId：店铺id
enabled：1可用；0停用
    }*/

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.ymt.sgr.kitchen.model;

/**
 * Data：2018/6/20/020-9:56
 * By  沈国荣
 * Description:
 */
public class User {
    String username;
    String password;
  /*  public User(String account, String password) {
        this.username = account;
        this.password = password;
    }*/

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

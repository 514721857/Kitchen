package com.ymt.sgr.kitchen.model;
/**
 * Created by 沈国荣 on 2018/6/19.
 * QQ:514721857
 * Description:
 */

public class Result<T> {
    public static final int RESULT_OK = 200;



    public String message;


    public String status;


    public T content;
}
package com.ymt.sgr.kitchen.http.webSocket;

import com.zhangke.websocket.Response;

/**
 * Created by ZhangKe on 2018/6/27.
 */
public class CommonResponse implements Response<String> {

    private String responseText;
    private String responseEntity;

    public CommonResponse(String responseText) {
        this.responseText = responseText;
        this.responseEntity = responseText;
    }

    @Override
    public String getResponseText() {
        return responseText;
    }

    @Override
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    @Override
    public String getResponseEntity() {
        return this.responseEntity;
    }

    @Override
    public void setResponseEntity(String responseEntity) {
        this.responseEntity = responseEntity;
    }
}

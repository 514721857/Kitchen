package com.ymt.sgr.kitchen.util;

import android.content.Context;

import java.lang.reflect.Method;


public class ContextUtil {
    /**
     * Context����
     */
    private static Context CONTEXT_INSTANCE;

    /**
     * ȡ��Context����
     * PS:���������̵߳���
     * @return Context
     */
    public static Context getContext() {
        if (CONTEXT_INSTANCE == null) {
            synchronized (ContextUtil.class) {
                if (CONTEXT_INSTANCE == null) {
                    try {
                        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");

                        Method method = ActivityThread.getMethod("currentActivityThread");
                        Object currentActivityThread = method.invoke(ActivityThread);//��ȡcurrentActivityThread ����

                        Method method2 = currentActivityThread.getClass().getMethod("getApplication");
                        CONTEXT_INSTANCE =(Context)method2.invoke(currentActivityThread);//��ȡ Context����

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CONTEXT_INSTANCE;
    }
}

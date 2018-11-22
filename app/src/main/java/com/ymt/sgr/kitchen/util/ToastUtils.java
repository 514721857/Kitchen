package com.ymt.sgr.kitchen.util;

import android.os.Handler;
import android.widget.Toast;

public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("T cannot be instantiated");
    }

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showShort(CharSequence text) {
        if (ContextUtil.getContext() != null) {
            mHandler.removeCallbacks(r);
            if (mToast != null) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(ContextUtil.getContext(), text, Toast.LENGTH_SHORT);
                mHandler.postDelayed(r, 500);
            }
            mToast.show();
        }
    }

    public static void showShort(int text) {
        if (ContextUtil.getContext() != null) {
            mHandler.removeCallbacks(r);
            if (mToast != null) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(ContextUtil.getContext(), text, Toast.LENGTH_SHORT);
                mHandler.postDelayed(r, 500);
            }
            mToast.show();
        }
    }

    public static void showLong(CharSequence text) {
        if (ContextUtil.getContext() != null) {
            mHandler.removeCallbacks(r);
            if (mToast != null) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(ContextUtil.getContext(), text, Toast.LENGTH_LONG);
                mHandler.postDelayed(r, 500);
            }
            mToast.show();
        }
    }

    public static void showLong(int text) {
        if (ContextUtil.getContext() != null) {
            mHandler.removeCallbacks(r);
            if (mToast != null) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(ContextUtil.getContext(), text, Toast.LENGTH_LONG);
                mHandler.postDelayed(r, 500);
            }
            mToast.show();
        }
    }

    /**
     * ��������ʧ��
     */
    public static void netError() {
        if (ContextUtil.getContext() != null) {
            String text = "��������,����������,���³��ԡ�";
            mHandler.removeCallbacks(r);
            if (mToast != null) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(ContextUtil.getContext(), text, Toast.LENGTH_LONG);
                mHandler.postDelayed(r, 200);
            }
            mToast.show();
        }
    }

}

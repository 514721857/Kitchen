package com.ymt.sgr.kitchen.view;

/**
 * Data：2018/6/22/022-14:09
 * By  沈国荣
 * Description:
 */

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ymt.sgr.kitchen.R;


public class MyDialog {

    public Dialog mDialog;
    public TextView dialog_message;
    public TextView positive;
    public TextView negative;

    public MyDialog(Context context, String message) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout, null);

        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        dialog_message = (TextView) view.findViewById(R.id.dialog_message);
        dialog_message.setText(message);

        positive = (TextView) view.findViewById(R.id.yes);
        negative = (TextView) view.findViewById(R.id.no);

    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}

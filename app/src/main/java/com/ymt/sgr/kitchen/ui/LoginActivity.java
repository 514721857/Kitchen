package com.ymt.sgr.kitchen.ui;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.ymt.sgr.kitchen.R;
import com.ymt.sgr.kitchen.config.AppCon;
import com.ymt.sgr.kitchen.http.HttpUtils;
import com.ymt.sgr.kitchen.model.CommonModel;
import com.ymt.sgr.kitchen.model.Result;
import com.ymt.sgr.kitchen.model.User;
import com.ymt.sgr.kitchen.ui.order.OrderActivity;
import com.ymt.sgr.kitchen.util.StartActivityUtil;
import com.ymt.sgr.kitchen.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_bt_commit)
    Button login_bt_commit;

    @BindView(R.id.login_ed_usename)
    EditText login_ed_usename;

    @BindView(R.id.login_ed_password)
    EditText login_ed_password;
    private Unbinder unbinder;

    CommonModel commonModel;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        commonModel=new CommonModel(this);
        pref = this.getSharedPreferences(AppCon.USER_KEY,MODE_PRIVATE);
        editor = pref.edit();
        String temp= pref.getString(AppCon.SCCESS_TOKEN_KEY,"");
        if(temp==null||temp.equals("")){
            login_ed_usename.setText(pref.getString(AppCon.USER_NAME,""));
            login_ed_password.setText(pref.getString(AppCon.USER_PWD,""));
        }else{
            StartActivityUtil.skipAnotherActivity(LoginActivity.this,OrderActivity.class);
            finish();
        }
    }

    @OnClick({ R.id.login_bt_commit,R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt_commit://登录

                StartActivityUtil.skipAnotherActivity(LoginActivity.this,OrderActivity.class);
                finish();

                if(login_ed_usename.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if(login_ed_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else{
                    User user=new User();
                    user.setPassword(login_ed_password.getText().toString());
                    user.setUsername(login_ed_usename.getText().toString());
                    commonModel.getLogin(user, new HttpUtils.OnHttpResultListener() {
                        @Override
                        public void onResult(Object result) {
                           Result<User> temp=(Result<User>)result;
                           if(temp.status.equals("200")){

                               if(!temp.content.getEnabled().equals("1")){
                                   ToastUtils.showLong("该账户已被停用");
                               }else if(!temp.content.getRole().equals("1")){
                                   ToastUtils.showLong("请用厨房账号登录");
                               }else{
                                   editor.putString(AppCon.SCCESS_TOKEN_KEY,temp.content.getToken());
                                   editor.putString(AppCon.USER_NAME,login_ed_usename.getText().toString());
                                   editor.putString(AppCon.USER_PWD,login_ed_password.getText().toString());
                                   editor.putString(AppCon.USER_SHOP_ID,temp.content.getShopId());
                                   editor.commit();
                                   StartActivityUtil.skipAnotherActivity(LoginActivity.this,OrderActivity.class);
                                   finish();
                               }


                           }else{
                               Toast.makeText(LoginActivity.this,temp.message,Toast.LENGTH_SHORT).show();
                           }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
                }


                break;
            case R.id.back://退出
                finish();
                break;



        }
    }

}

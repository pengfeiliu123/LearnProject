package com.lpf.bmob;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lpf.common.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liupengfei on 17/2/7.
 */

public class RegisterOrLoginActivity extends AppCompatActivity {

    private TextView etName;
    private TextView etPassword;
    private TextView etEmail;

    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;

        initViews();
    }

    private void initViews() {
        etName = (TextView) findViewById(R.id.et_username);
        etPassword = (TextView) findViewById(R.id.et_password);
        etEmail = (TextView) findViewById(R.id.et_email);

        //register
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser user = new BmobUser();

                user.setUsername(etName.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setEmail(etEmail.getText().toString());

                user.signUp(new SaveListener<Object>() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (null == e) {
                            Toast.makeText(RegisterOrLoginActivity.this, "register success", Toast.LENGTH_SHORT).show();
                        } else {
                            int errorCode = e.getErrorCode();
                            switch (errorCode){
                                case 202:
                                    ToastUtil.shortShow(RegisterOrLoginActivity.this,"username is already taken.");
                                    break;
                                case 203:
                                    ToastUtil.shortShow(RegisterOrLoginActivity.this,"email is already taken.");
                                    break;
                            }
                            Toast.makeText(RegisterOrLoginActivity.this, "register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser user = new BmobUser();

                user.setUsername(etName.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setEmail("test@gamil.com");

                user.login(new SaveListener<Object>() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if(null == e){
                            ToastUtil.shortShow(mContext,"login success,jumping...");
                        }else{
                            int resultCode = e.getErrorCode();
                            if(resultCode == 101){
                                ToastUtil.shortShow(mContext,"login failed, username or password was wrong!");
                            }
                        }
                    }
                });
            }
        });
    }
}


/**
 * 9001	AppKey is Null, Please initialize BmobSDK.	Application Id为空，请初始化.
 9002	Parse data error	解析返回数据出错
 9003	upload file error	上传文件出错
 9004	upload file failure	文件上传失败
 9005	A batch operation can not be more than 50	批量操作只支持最多50条
 9006	objectId is null	objectId为空
 9007	BmobFile File size must be less than 10M.	文件大小超过10M
 9008	BmobFile File does not exist.	上传文件不存在
 9009	No cache data.	没有缓存数据
 9010	The network is not normal.(Time out)	网络超时
 9011	BmobUser does not support batch operations.	BmobUser类不支持批量操作
 9012	context is null.	上下文为空
 9013	BmobObject Object names(database table name) format is not correct.	BmobObject（数据表名称）格式不正确
 9014	第三方账号授权失败	第三方账号授权失败
 9015	其他错误均返回此code	其他错误均返回此code
 9016	The network is not available,please check your network!	无网络连接，请检查您的手机网络.
 9017	与第三方登录有关的错误，具体请看对应的错误描述	与第三方登录有关的错误，具体请看对应的错误描述
 9018	参数不能为空	参数不能为空
 9019	格式不正确：手机号码、邮箱地址、验证码	格式不正确：手机号码、邮箱地址、验证码
 */

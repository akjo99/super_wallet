package com.sup.superwallet.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;
import com.sup.superwallet.R;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.JsonBean;
import com.sup.superwallet.bean.LoginBean;
import com.sup.superwallet.interfaces.OnCountDownTimerListener;
import com.sup.superwallet.utils.CountDownTimerUtils;
import com.sup.superwallet.utils.HttpClient;
import com.sup.superwallet.utils.JsonCallBack;

import butterknife.BindView;
import io.branch.referral.Branch;
import io.branch.referral.util.BranchEvent;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.ed_mobile)
    EditText mobile;
    @BindView(R.id.ed_code)
    EditText code;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_protocal)
    TextView tvProtocal;
    @BindView(R.id.cb_agree)
    TextView cbAgree;
    private boolean isSel;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        //验证码倒计时
        CountDownTimerUtils.getInstance().setOnCountDownTimerListener(new OnCountDownTimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCode.setEnabled(false);
                tvCode.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvCode.setEnabled(true);
                tvCode.setText("Send");
            }
        });

    }

    @Override
    protected void initListener() {

        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();

            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        tvProtocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                intent.putExtra("url", Api.USER_PROTOCAL);
                intent.putExtra("title", "Privacy Agreement");
                startActivity(intent);
            }
        });

        final Drawable drawableLeft = getResources().getDrawable(
                R.drawable.ic_checkbox);
        final Drawable drawable = getResources().getDrawable(
                R.drawable.ic_checkbox_n);
        isSel = true;
        cbAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSel) {
                    cbAgree.setCompoundDrawablesWithIntrinsicBounds(drawable,
                            null, null, null);
                } else {
                    cbAgree.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                }
                isSel = !isSel;
            }
        });

    }

    @Override
    protected void initData() {

    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String deviceID = Settings.System.getString(this.getContentResolver(), Settings.System.ANDROID_ID);
        String num = mobile.getText().toString().trim();
        //将设备号存入缓存
        SPUtils.getInstance().put("deviceID", deviceID);
        SPUtils.getInstance().put("mobile", num);
        OkGo.post(Api.SEND)
                .params("mobile", num)
                .params("imei", deviceID)
                .execute(new JsonCallBack() {
                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        JsonBean jsonBean = gson.fromJson(json, JsonBean.class);
                        if ("200".equals(jsonBean.getCode())) {
                            CountDownTimerUtils.getInstance().startTimer();
                            ToastUtils.showShort(jsonBean.getMsg());
                        } else {
                            ToastUtils.showShort(jsonBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    private void login() {
        String phoneNum = mobile.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
//            mEditPhone.setError("请输入您的手机号");
//            mEditPhone.requestFocus();
            ToastUtils.showShort("Please enter correct mobile number");
            return;
        }
//        if (!ValidatePhoneUtil.validateMobileNumber(phoneNum)) {
//            mEditPhone.setError("请输入正确的手机号码"));
//            mEditPhone.requestFocus();
//            return;
//        }
        String smsCode = code.getText().toString().trim();
        if (TextUtils.isEmpty(smsCode)) {
//            mTvCode.setError("请输入验证码");
//            mTvCode.requestFocus();
            ToastUtils.showShort("Please enter verifycation code");
            return;
        }
        if (!isSel) {
            ToastUtils.showShort("Please agree the Terms and Conditions");
            return;
        }
        OkGo.post(Api.LOGIN)
                .params("mobile", phoneNum)
                .params("code", smsCode)
                .execute(new JsonCallBack() {
                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                        tvLogin.setEnabled(false);
                    }

                    @Override
                    public void onResponse(String json) {
                        tvLogin.setEnabled(true);
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(json, LoginBean.class);
                        if ("200".equals(loginBean.getCode())) {
                            //将token与uid存入缓存
                            SPUtils.getInstance().put("token", loginBean.getData().getToken());
                            SPUtils.getInstance().put("uid", loginBean.getData().getUid());
                            //将已登录状态存入缓存
                            SPUtils.getInstance().put("isLogin", true);
                            Log.d("Login", "token为：" + loginBean.getData().getToken());
                            Log.d("Login", "uid为：" + loginBean.getData().getUid());
                            //根据字段去判断是否设置branch

                            //初始化头部请求
                            HttpClient.getInstance().init(getApplication());
                            //设置branch
                            Branch.getInstance().setIdentity(loginBean.getData().getUid() + "");
                            new BranchEvent("user_login")
                                    .addCustomDataProperty("uid", loginBean.getData().getUid() + "")
                                    .addCustomDataProperty("mobile", phoneNum)
                                    .setCustomerEventAlias("user_login")
                                    .logEvent(LoginActivity.this);
                            //设置facebook
                            if (1 == loginBean.getData().getUser_info().getIs_new()) {
                                AppEventsLogger logger = AppEventsLogger.newLogger(LoginActivity.this);
//                                    logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION);
                                Bundle params = new Bundle();
                                params.putString(AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD, "googleplay");
                                logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, params);
                                Log.d("Login","这里执行啦");
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtils.showShort(loginBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}

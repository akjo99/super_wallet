package com.sup.superwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.sup.superwallet.R;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.bean.SubmitInfoBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.bean.VerifyStepInfoBean;
import com.sup.superwallet.utils.BottomDialog;
import com.sup.superwallet.utils.JsonCallBack;
import com.sup.superwallet.utils.WheelView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.annotations.Beta;

public class BaseInforActivity extends BaseActivity {

    @BindView(R.id.tv_text4)
    TextView tvText4;
    @BindView(R.id.tv_text4_1)
    TextView tvText4_1;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_email_address)
    EditText edEmail;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_sumbit)
    TextView tvSubmit;

    @BindView(R.id.ed_age)
    EditText edAge;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_marriage)
    TextView tvMarriage;


    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;

    private BottomDialog bottomDialog;
    private String sex ="";
    private String age = "";
    private String education = "";
    private String marriage = "";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String str = (String) msg.obj;
                tvGender.setText(str);
            }else if (msg.what == 3) {
                String str = (String) msg.obj;
                tvEducation.setText(str);
            } else if (msg.what == 4) {
                String str = (String) msg.obj;
                tvMarriage.setText(str);
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_basic_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        getTips();
        getVerifyStepInfo();

    }

    @Override
    protected void initListener() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInfo();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_gender,R.id.ll_education,R.id.ll_marriage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_gender:
                List<String> sexS = new ArrayList<>();
                sexS.add("Male");
                sexS.add("Female");
                limits(sexS);
                break;
            case R.id.ll_education:
                List<String> education = new ArrayList<>();
                education.add("University or above");
                education.add("University");
                education.add("High school");
                education.add("Below high school");
                setEducation(education);
                break;
            case R.id.ll_marriage:
                List<String> marriage = new ArrayList<>();
                marriage.add("Single");
                marriage.add("Married");
                marriage.add("Divorced");
                marriage.add("Widowed");
                setMarriage(marriage);
                break;
        }
    }

    /**
     * 设置教育信息
     */
    private void setEducation(List<String> limits) {
        if (bottomDialog != null && bottomDialog.isShowing()) {
            return;
        }
        View outerView1 = LayoutInflater.from(this).inflate(R.layout.dialog_repayment, null);
        final WheelView wv1 = outerView1.findViewById(R.id.wv1);
        wv1.setItems(limits, 0);
        TextView tv_ok = outerView1.findViewById(R.id.tv_ok);
        TextView tv_cancel = outerView1.findViewById(R.id.tv_cancel);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
                String nTimeLimit = wv1.getSelectedItem();
                if ("University or above".equals(nTimeLimit)) {
                    education = "University or above";
                } else if ("University".equals(nTimeLimit)) {
                    education = "University";
                } else if ("High school".equals(nTimeLimit)) {
                    education = "High school";
                } else if ("Below high school".equals(nTimeLimit)) {
                    education = "Below high school";
                }
                Message message = new Message();
                message.what = 3;
                message.obj = education;
                handler.sendMessage(message);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog = new BottomDialog(this, R.style.ActionSheetDialogStyle);
        bottomDialog.setContentView(outerView1);
        bottomDialog.show();

    }

    /**
     * 设置婚姻状况
     */
    private void setMarriage(List<String> limits) {

        if (bottomDialog != null && bottomDialog.isShowing()) {
            return;
        }
        View outerView1 = LayoutInflater.from(this).inflate(R.layout.dialog_repayment, null);
        final WheelView wv1 = outerView1.findViewById(R.id.wv1);
        wv1.setItems(limits, 0);
        TextView tv_ok = outerView1.findViewById(R.id.tv_ok);
        TextView tv_cancel = outerView1.findViewById(R.id.tv_cancel);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
                String nTimeLimit = wv1.getSelectedItem();
                if ("Single".equals(nTimeLimit)) {
                    marriage = "Single";
                } else if ("Married".equals(nTimeLimit)) {
                    marriage = "Married";
                } else if ("Divorced".equals(nTimeLimit)) {
                    marriage = "Divorced";
                } else if ("Widowed".equals(nTimeLimit)) {
                    marriage = "Widowed";
                }
                Message message = new Message();
                message.what = 4;
                message.obj = marriage;
                handler.sendMessage(message);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog = new BottomDialog(this, R.style.ActionSheetDialogStyle);
        bottomDialog.setContentView(outerView1);
        bottomDialog.show();


    }


    /**
     * 调取获取文字信息的接口
     */

    private void getTips() {
        OkGo.post(Api.TEXT_TIPS)
                .execute(new JsonCallBack() {
                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                        multipleStatusView.showLoading();
                    }

                    @Override
                    public void onResponse(String json) {
                        multipleStatusView.showContent();
                        Gson gson = new Gson();
                        TextTipsBean textTipsBean = gson.fromJson(json, TextTipsBean.class);
                        if ("200".equals(textTipsBean.getCode())) {
                            tvText4.setText(textTipsBean.getText().getText4());
                            tvText4_1.setText(textTipsBean.getText().getText4_1());
                        }else {
                            ToastUtils.showShort(textTipsBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }
    /**
     * 设置性别
     */
    private void limits(List<String> limits) {
        if (bottomDialog != null && bottomDialog.isShowing()) {
            return;
        }
        View outerView1 = LayoutInflater.from(this).inflate(R.layout.dialog_repayment, null);
        final WheelView wv1 = outerView1.findViewById(R.id.wv1);
        wv1.setItems(limits, 0);
        TextView tv_ok = outerView1.findViewById(R.id.tv_ok);
        TextView tv_cancel = outerView1.findViewById(R.id.tv_cancel);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
                String nTimeLimit = wv1.getSelectedItem();
                if ("Male".equals(nTimeLimit)) {
                    sex = "Male";
                } else if ("Female".equals(nTimeLimit)) {
                    sex = "Female";
                }
                Message message = new Message();
                message.what = 1;
                message.obj = sex;
                handler.sendMessage(message);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog = new BottomDialog(this, R.style.ActionSheetDialogStyle);
        bottomDialog.setContentView(outerView1);
        bottomDialog.show();
    }

    /**
     * 向后台提交基本信息
     */
    private void sendInfo() {
        if (TextUtils.isEmpty(edName.getText().toString().trim())){
            ToastUtils.showShort("Please check your name.");
            return;
        }
        if (TextUtils.isEmpty(edEmail.getText().toString().trim())){
            ToastUtils.showShort("Please check your email.");
            return;
        }
        if (TextUtils.isEmpty(tvGender.getText().toString().trim())){
            ToastUtils.showShort("Please check your Gender.");
            return;
        }
        if (TextUtils.isEmpty(edAge.getText().toString().trim())){
            ToastUtils.showShort("Please check your Age.");
            return;
        }
        if (TextUtils.isEmpty(tvEducation.getText().toString().trim())){
            ToastUtils.showShort("Please check your Education information.");
            return;
        }
        if (TextUtils.isEmpty(tvMarriage.getText().toString().trim())){
            ToastUtils.showShort("Please check your Marriage information.");
            return;
        }
        OkGo.post(Api.SUBMIT_INFO)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .params("Name", edName.getText().toString().trim())
                .params("Email", edEmail.getText().toString().trim())
                .params("Gender", tvGender.getText().toString().trim())
                .params("Age", edAge.getText().toString().trim())
                .params("Education", tvEducation.getText().toString().trim())
                .params("Marriage", tvMarriage.getText().toString().trim())
                .execute(new JsonCallBack() {

                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                        tvSubmit.setEnabled(false);
                    }

                    @Override
                    public void onResponse(String json) {
                        tvSubmit.setEnabled(true);
                        Gson gson = new Gson();
                        SubmitInfoBean submitInfoBean = gson.fromJson(json, SubmitInfoBean.class);
                        if ("200".equals(submitInfoBean.getCode())) {
                            ToastUtils.showShort("Submit Success！");
                            //立即进行后台控制的验证
                            step();
                        } else {
                            ToastUtils.showShort(submitInfoBean.getMsg());
                            return;
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });

    }

    /**
     * 获取填写的基本信息
     */
    private void getVerifyStepInfo() {
        OkGo.post(Api.VERIFYSTEPINFO)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .execute(new JsonCallBack() {

                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                        multipleStatusView.showLoading();
                    }

                    @Override
                    public void onResponse(String json) {
                        multipleStatusView.showContent();
                        Gson gson = new Gson();
                        VerifyStepInfoBean verifyStepInfoBean = gson.fromJson(json, VerifyStepInfoBean.class);
                        if ("200".equals(verifyStepInfoBean.getCode())) {
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getName())){
                                edName.setText(verifyStepInfoBean.getUSER().getName());
                            }
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getEmail())) {
                                edEmail.setText(verifyStepInfoBean.getUSER().getEmail());
                            }
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getId_sex())) {
                                tvGender.setText(verifyStepInfoBean.getUSER().getId_sex());
                            }
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getId_age())) {
                                edAge.setText(verifyStepInfoBean.getUSER().getId_age());
                            }
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getSchool())) {
                                tvEducation.setText(verifyStepInfoBean.getUSER().getSchool());
                            }
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getMarriage()))
                                tvMarriage.setText(verifyStepInfoBean.getUSER().getMarriage());
                        } else {
                            ToastUtils.showShort(verifyStepInfoBean.getMsg());
                        }

                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 后台控制的验证
     */
    public void step() {
//        StepOneActivity.forward(this);

//        case ‘FaceAuth’: 人脸验证
//        case ‘IDAuth’: 身份证验证
//        case ‘CombineAuth’: 三合一聚合验证
//        case ‘BasicInfo’: 基础信息
//        case ‘MountVerify’: 额度匹配
//        case ‘Wallet’: 钱包
//        case ‘BindCard’: 绑卡
//        case ‘PaymentSelect’: 支付页
//        case ‘Finish’: 流程结束
        String stringValue = "BasicInfo";
        OkGo.post(Api.VERIFYSTEP)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .params("currentStep", stringValue)
                .execute(new JsonCallBack() {
                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        StepInfoBean stepInfoBean = gson.fromJson(json, StepInfoBean.class);
                        if ("200".equals(stepInfoBean.getCode())) {
                            String nextStep = stepInfoBean.getData().getNextStep();
                            Log.d("BaseInforActivity","nextStep为："+nextStep);
                            if ("IDAuth".equals(nextStep)){
                                Intent intent = new Intent(BaseInforActivity.this,IDAuthActivity.class);
                                startActivity(intent);
                                finish();
                            }else if("BasicInfo".equals(nextStep)){
                                Intent intent = new Intent(BaseInforActivity.this,NewBaseInforActivity.class);
                                startActivity(intent);
                                finish();
                            }else if ("MountVerify".equals(nextStep)){
                                Intent intent = new Intent(BaseInforActivity.this,AuthenticationActivity.class);
                                startActivity(intent);
                                finish();
                            }else if ("BindCard".equals(nextStep)){
                                Intent intent = new Intent(BaseInforActivity.this,BankActivity.class);
                                startActivity(intent);
                                finish();
                            }else if ("PaymentSelect".equals(nextStep)){
                                Intent intent = new Intent(BaseInforActivity.this,PayActivity.class);
                                startActivity(intent);
                                finish();
                            }else if ("Finish".equals(nextStep)){
                                Intent intent = new Intent(BaseInforActivity.this,MainActivity.class);
                                startActivity(intent);
                                SPUtils.getInstance().put("is_vip", true);
                                finish();
                            }else if ("Wallet".equals(nextStep)){
                                Intent intent = new Intent(BaseInforActivity.this,WalletActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onError(String json) {

                    }
                });
    }

}

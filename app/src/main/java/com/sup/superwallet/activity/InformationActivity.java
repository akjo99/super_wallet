package com.sup.superwallet.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;
import com.sup.superwallet.R;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.bean.VerifyStepInfoBean;
import com.sup.superwallet.utils.JsonCallBack;

import butterknife.BindView;
import butterknife.OnClick;
import io.branch.referral.util.Product;

public class InformationActivity extends BaseActivity {

    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.tv_personal_cer)
    TextView tvPersonalCer;
    @BindView(R.id.tv_employment_cer)
    TextView tvEmploymentCer;
    @BindView(R.id.tv_bank_cer)
    TextView tvBankCer;
    @BindView(R.id.tv_loan)
    TextView tvLoan;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;


    @Override
    protected int getLayout() {
        return R.layout.activity_information;
    }

    public static void forward(Context context, int type) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getTips();
        getVerifyStepInfo();
        tvTitles.setText("My Profile");
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_person_information, R.id.ll_Employment, R.id.ll_bank_information})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_person_information:
//                Intent intent = new Intent(InformationActivity.this, AuthenticationActivity.class);
                Intent intent = new Intent(InformationActivity.this,CameraStepActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_Employment:
                Intent intent1 = new Intent(InformationActivity.this, EmploymentActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_bank_information:
                Intent intent2 = new Intent(InformationActivity.this, BankActivity.class);
                startActivity(intent2);
                break;
        }
    }


    /**
     * 获取提示文字
     */

    private void getTips() {
        OkGo.post(Api.TEXT_TIPS)
                .execute(new JsonCallBack() {
                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        TextTipsBean textTipsBean = gson.fromJson(json, TextTipsBean.class);
                        if ("200".equals(textTipsBean.getCode())) {
                            tvText2.setText(textTipsBean.getText().getText2());
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 获取认证的状态
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
                            if (1 == verifyStepInfoBean.getData().getVerifyStepInfo().get(0)) {
                                tvPersonalCer.setText("Certified");
                                tvPersonalCer.setTextColor(Color.parseColor("#FF6811"));
                            }
                            if (1 == verifyStepInfoBean.getData().getVerifyStepInfo().get(1)) {
                                tvEmploymentCer.setText("Certified");
                                tvEmploymentCer.setTextColor(Color.parseColor("#FF6811"));
                            }
                            if (1 == verifyStepInfoBean.getData().getVerifyStepInfo().get(2)) {
                                tvBankCer.setText("Certified");
                                tvBankCer.setTextColor(Color.parseColor("#FF6811"));
                            }

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
     * onResume
     */
    @Override
    protected void onResume() {
        super.onResume();
        getVerifyStepInfo();

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
        OkGo.post(Api.VERIFYSTEP)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .params("currentStep", "Cinfo")
                .execute(new JsonCallBack() {
                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                        multipleStatusView.showLoading();
                    }

                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        StepInfoBean stepInfoBean = gson.fromJson(json, StepInfoBean.class);
                        if ("200".equals(stepInfoBean.getCode())) {
                            String nextStep = stepInfoBean.getData().getNextStep();
                            String firstStep = stepInfoBean.getData().getFirstStep();
                            if (nextStep.equals("PaymentSelect")){
                                Intent intent1 = new Intent(InformationActivity.this,PayActivity.class);
                                startActivity(intent1);
                                InformationActivity.this.finish();
                            }else if (nextStep.equals("finish")){
                                SPUtils.getInstance().put("is_vip",true);
                                Intent intent2 = new Intent(InformationActivity.this, MainActivity.class);
                                startActivity(intent2);
                                InformationActivity.this.finish();
                            }else if (nextStep.equals("BasicInfo")){
                                Intent intent3 = new Intent(InformationActivity.this, AuthenticationActivity.class);
                                startActivity(intent3);
                            }else if (nextStep.equals("employment")){
                                Intent intent4 = new Intent(InformationActivity.this, EmploymentActivity.class);
                                startActivity(intent4);
                            }else if (nextStep.equals("BindCard")){
                                Intent intent5 = new Intent(InformationActivity.this, BankActivity.class);
                                startActivity(intent5);
                            }else  {
                                multipleStatusView.showContent();
                                ToastUtils.showShort("Please check your information！");
                            }

                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }
}



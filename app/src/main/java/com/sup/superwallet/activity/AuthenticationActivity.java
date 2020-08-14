package com.sup.superwallet.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.sup.superwallet.fragment.MineFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;
import com.sup.superwallet.R;
import com.sup.superwallet.adapter.AuthenticationAdapter;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.AuthBean;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.bean.SubmitInfoBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.bean.VerifyStepInfoBean;
import com.sup.superwallet.utils.BottomDialog;
import com.sup.superwallet.utils.Common;
import com.sup.superwallet.utils.JsonCallBack;
import com.sup.superwallet.utils.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticationActivity extends BaseActivity {

    //此页面变为审核页面
    @BindView(R.id.tv_text5)
    TextView tvText5;
    @BindView(R.id.iv_auth)
    ImageView ivAuth;
    @BindView(R.id.iv_line_bg)
    ImageView ivLinebg;
    @BindView(R.id.iv_rs)
    ImageView ivRs;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String text6 = (String) msg.obj;
                ivAuth.setVisibility(View.INVISIBLE);
                ivLinebg.setVisibility(View.INVISIBLE);
                creatDialog(text6);

            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getTips();
        //启动动画效果
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivAuth, "translationX", 0f, -200f, 0f, 200f, 0f, -200f, 0f, 200f, 0f, -200f, 0f);
        animator.setDuration(5000);
        animator.start();
        setAnimator1();

    }

    private void setAnimator1(){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivRs,"rotation",0f,45f,0f,-45f,0f);
        animator1.setDuration(3000);
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            setAnimator1();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator1.start();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 调取获取文字信息的接口
     */

    private void getTips() {
        OkGo.post(Api.TEXT_TIPS)
                .execute(new JsonCallBack() {
                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        TextTipsBean textTipsBean = gson.fromJson(json, TextTipsBean.class);
                        if ("200".equals(textTipsBean.getCode())) {
                            tvText5.setText(textTipsBean.getText().getText5().replace("\\n", "\n"));
                            String text6 = textTipsBean.getText().getText6();
                            Message message = new Message();
                            message.what = 1;
                            message.obj = text6;
                            handler.sendMessageDelayed(message, 5000);
                        } else {
                            ToastUtils.showShort(textTipsBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 支付失败所对应的弹窗
     */

    private void creatDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthenticationActivity.this);
        View v = LayoutInflater.from(AuthenticationActivity.this).inflate(R.layout.dialog_amount_money, null, false);
        ViewHolder viewHolder = new ViewHolder(v);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        viewHolder.rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog.dismiss();
                //前往绑卡的页面
//                Intent intent = new Intent(AuthenticationActivity.this,BankActivity.class);
////                startActivity(intent);
                String stringValue = "MountVerify";
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
                                    Log.d("BankActivity", "nextStep为：" + nextStep);
                                    if ("IDAuth".equals(nextStep)) {
                                        Intent intent = new Intent(AuthenticationActivity.this, IDAuthActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if ("BasicInfo".equals(nextStep)) {
                                        Intent intent = new Intent(AuthenticationActivity.this, NewBaseInforActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if ("MountVerify".equals(nextStep)) {
                                        Intent intent = new Intent(AuthenticationActivity.this, AuthenticationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if ("BindCard".equals(nextStep)) {
                                        Intent intent = new Intent(AuthenticationActivity.this, BankActivity.class);
                                        startActivity(intent);
                                        //dialog.dismiss();
                                        finish();
                                    } else if ("PaymentSelect".equals(nextStep)) {
                                        Intent intent = new Intent(AuthenticationActivity.this, NewPayActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if ("Finish".equals(nextStep)) {
                                        Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        SPUtils.getInstance().put("is_vip", true);
                                        finish();
                                    } else if ("Wallet".equals(nextStep)) {
                                        Intent intent = new Intent(AuthenticationActivity.this, WalletActivity.class);
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
        });
        viewHolder.tvMoney.setText("₹" + SPUtils.getInstance().getString("myquota"));
        viewHolder.tvText6.setText(text);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_parent)
        RelativeLayout rlParent;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_text6)
        TextView tvText6;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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
        String stringValue = "MountVerify";
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
                            Log.d("BankActivity", "nextStep为：" + nextStep);
                            if ("IDAuth".equals(nextStep)) {
                                Intent intent = new Intent(AuthenticationActivity.this, IDAuthActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("BasicInfo".equals(nextStep)) {
                                Intent intent = new Intent(AuthenticationActivity.this, NewBaseInforActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("MountVerify".equals(nextStep)) {
                                Intent intent = new Intent(AuthenticationActivity.this, AuthenticationActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("BindCard".equals(nextStep)) {
                                Intent intent = new Intent(AuthenticationActivity.this, BankActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("PaymentSelect".equals(nextStep)) {
                                Intent intent = new Intent(AuthenticationActivity.this, NewPayActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("Finish".equals(nextStep)) {
                                Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                                startActivity(intent);
                                SPUtils.getInstance().put("is_vip", true);
                                finish();
                            } else if ("Wallet".equals(nextStep)) {
                                Intent intent = new Intent(AuthenticationActivity.this, WalletActivity.class);
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

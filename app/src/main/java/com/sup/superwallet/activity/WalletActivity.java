package com.sup.superwallet.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.sup.superwallet.R;
import com.sup.superwallet.adapter.WalletAdapter;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.bean.WalletBean;
import com.sup.superwallet.fragment.MineFragment;
import com.sup.superwallet.utils.JsonCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class WalletActivity extends BaseActivity {
    @BindView(R.id.tv_text7)
    TextView tvText7;
    @BindView(R.id.tv_text7_1)
    TextView tvText7_1;
    @BindView(R.id.tv_text7_2)
    TextView tvText7_2;
    @BindView(R.id.tv_text7_3)
    TextView tvText7_3;
    @BindView(R.id.tv_text7_5)
    TextView tvText7_5;
    @BindView(R.id.tv_sumbit)
    TextView tvSubmit;
    @BindView(R.id.tv_my_quota)
    TextView tvMyQuota;
    @BindView(R.id.rc_wallet)
    RecyclerView rcWallet;
    @BindView(R.id.iv_no_data)
    ImageView ivNoData;

    @BindView(R.id.tv_amount_1)
    TextView tvAmount_1;
    @BindView(R.id.tv_amount_2)
    TextView tvAmount_2;
    @BindView(R.id.tv_amount_3)
    TextView tvAmount_3;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.tv_bank_number)
    TextView tvBankNum;

//    @BindView(R.id.tv_interest)
//    TextView tvInterest;

    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;



    private WalletAdapter walletAdapter;
    private String text7_5;
    private List<WalletBean.OrderBean> orderBeans = new ArrayList<>();
    @Override
    protected int getLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getTips();
        rcWallet.setLayoutManager(new LinearLayoutManager(this));
        walletAdapter = new WalletAdapter(orderBeans);
        rcWallet.setAdapter(walletAdapter);
    }

    @Override
    protected void initListener() {
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvBankNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到银行卡页面
                Intent intent = new Intent(WalletActivity.this,BankActivity.class);
                startActivity(intent);
            }
        });
//        ivTran.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //显示弹窗
//                creatDialog1(text7_5);
//
//            }
//        });
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
                    public void onStart(Request request) {
                        super.onStart(request);
                        multipleStatusView.showLoading();
                    }

                    @Override
                    public void onResponse(String json) {
                        getWalletData();
                        Gson gson = new Gson();
                        TextTipsBean textTipsBean = gson.fromJson(json, TextTipsBean.class);
                        if ("200".equals(textTipsBean.getCode())) {
                            tvText7.setText(textTipsBean.getText().getText7().replace("\\n","\n"));
                            tvText7_1.setText(textTipsBean.getText().getText7_1().replace("\\n","\n"));
                            tvText7_2.setText(textTipsBean.getText().getText7_2().replace("\\n","\n"));
                            tvText7_3.setText(textTipsBean.getText().getText7_3().replace("\\n","\n"));
                            tvText7_5.setText(textTipsBean.getText().getText7_5().replace("\\n","\n"));
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
        String stringValue = "Wallet";
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
                            if ("FaceAuth".equals(nextStep)) {
                                Intent intent = new Intent(WalletActivity.this, CameraStepActivity.class);
                                startActivity(intent);
                            } else if ("IDAuth".equals(nextStep)) {
                                Intent intent = new Intent(WalletActivity.this, IDAuthActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("BasicInfo".equals(nextStep)) {
                                Intent intent = new Intent(WalletActivity.this, NewBaseInforActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("MountVerify".equals(nextStep)) {
                                Intent intent = new Intent(WalletActivity.this, AuthenticationActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("BindCard".equals(nextStep)) {
                                Intent intent = new Intent(WalletActivity.this, BankActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("PaymentSelect".equals(nextStep)) {
                                Intent intent = new Intent(WalletActivity.this, NewPayActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("Finish".equals(nextStep)) {
                                Intent intent = new Intent(WalletActivity.this, MainActivity.class);
                                startActivity(intent);
                                SPUtils.getInstance().put("is_vip", true);
                                finish();
                            }else if ("Wallet".equals(nextStep)){
                                Intent intent = new Intent(WalletActivity.this,WalletActivity.class);
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

    /**
     * 请求钱包页面的基本数据
     */
    private void getWalletData(){
        OkGo.post(Api.WALLET)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .execute(new JsonCallBack() {
                    @Override
                    public void onStart(Request request) {
                        multipleStatusView.showLoading();
                        super.onStart(request);
                    }

                    @Override
                    public void onResponse(String json) {
                        multipleStatusView.showContent();
                        Gson gson = new Gson();
                        WalletBean walletBean = gson.fromJson(json,WalletBean.class);
                        if ("200".equals(walletBean.getCode())){
                            tvMyQuota.setText("₹"+walletBean.getOption().getMay_quota());
                            tvBankNum.setText(walletBean.getBank_card().replace("","\t"));
//                            tvInterest.setText(walletBean.getTotalInterest());
                            //将此数据存入缓存
                            SPUtils.getInstance().put("myQuota",walletBean.getOption().getMay_quota());
                            tvAmount_1.setText("₹"+walletBean.getOption().getMay_quota());
                            tvAmount_2.setText(walletBean.getTransfered());
                            tvAmount_3.setText(walletBean.getTotalInterest());
                            if (walletBean.getOrder().size()>0){
                                ivNoData.setVisibility(View.GONE);
                                tvText7_5.setVisibility(View.GONE);
                                rcWallet.setVisibility(View.VISIBLE);
                                walletAdapter.setNewData(walletBean.getOrder());
                            }else if (walletBean.getOrder().size() ==0){
                                ivNoData.setVisibility(View.VISIBLE);
                                tvText7_5.setVisibility(View.VISIBLE);
                                rcWallet.setVisibility(View.GONE);
                            }

                        }else {
                            ToastUtils.showShort(walletBean.getCode());
                        }


                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }


    private void creatDialog1(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WalletActivity.this);
        View v = LayoutInflater.from(WalletActivity.this).inflate(R.layout.dialog_email_1, null, false);
        ViewHolder viewHolder1 = new ViewHolder(v);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();
        viewHolder1.tvEmail.setText(text);
        viewHolder1.tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_email)
        TextView tvEmail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}

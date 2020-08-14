package com.sup.superwallet.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.sup.superwallet.activity.AuthenticationActivity;
import com.sup.superwallet.activity.BaseInforActivity;
import com.sup.superwallet.activity.CameraStepActivity;
import com.sup.superwallet.activity.IDAuthActivity;
import com.google.gson.Gson;
import com.sup.superwallet.activity.NewBaseInforActivity;
import com.sup.superwallet.activity.NewPayActivity;
import com.sup.superwallet.activity.WalletActivity;
import com.sup.superwallet.bean.AllDataBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;
import com.sup.superwallet.R;
import com.sup.superwallet.activity.BankActivity;
import com.sup.superwallet.activity.LoginActivity;
import com.sup.superwallet.activity.MainActivity;
import com.sup.superwallet.activity.PayActivity;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseFragment;
import com.sup.superwallet.bean.HomeJsonBean;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.utils.JsonCallBack;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_text1)
    TextView tvText1;
    @BindView(R.id.tv_text1_1)
    TextView tvText1_1;
    @BindView(R.id.tv_apply)
    TextView tvApply;
    //@BindView(R.id.tv_money)
    //TextView tvMoney;
    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_progress_1)
    TextView tvProgress1;
    @BindView(R.id.tv_progress_2)
    TextView tvProgress2;
    @BindView(R.id.tv_progress_3)
    TextView tvProgress3;
    @BindView(R.id.tv_progress_4)
    TextView tvProgress4;
    @BindView(R.id.progress_home)
    ProgressBar progressBar;
    @BindView(R.id.tv_home_borrow)
    TextView ivBorrow;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        getHomeData();
        getTips();
        // initFlipper();
        //getAllData();
    }


    @Override
    protected void initListener() {

        ivBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLogin = SPUtils.getInstance().getBoolean("isLogin", false);
                if (!isLogin) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    step();
//                    Intent intent = new Intent(getContext(), NewNewBaseInforActivity.class);
//                    startActivity(intent);
//                    Intent intent = new Intent(getContext(), NewPayActivity.class);
//                    startActivity(intent);

                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getHomeData();
    }

    /**
     *
     * 获取金钱等数据
     */
    private void getHomeData() {
        OkGo.post(Api.HOME_DATA)
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
                        HomeJsonBean homeJsonBean = gson.fromJson(json, HomeJsonBean.class);
                        if ("200".equals(homeJsonBean.getCode())) {
                            // tvMoney.setText(homeJsonBean.getData().getOption_value().getQuota_value());
                            //将金钱数存入缓存
                            SPUtils.getInstance().put("quota", homeJsonBean.getData().getOption_value().getQuota_value());
                            SPUtils.getInstance().put("myquota", homeJsonBean.getData().getOption_value().getMay_quota());
                            tvMax.setText(homeJsonBean.getData().getOption_value().getQuota_value());

                            //获取进度
                            String progress = homeJsonBean.getData().getProgress();
                            boolean isLogin = SPUtils.getInstance().getBoolean("isLogin", false);
                            if (!isLogin){
                                tvProgress.setText("0%");
                                tvProgress1.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress2.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress3.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress4.setTextColor(getResources().getColor(R.color.colorWhite));
                            } else if ("25%".equals(progress)){
                                progressBar.setProgress(25);
                                tvProgress1.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress2.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress3.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress4.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress.setText(""+progress);
                            }else if ("50%".equals(progress)){
                                progressBar.setProgress(50);
                                tvProgress1.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress2.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress3.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress4.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress.setText(""+progress);
                            }else if ("75%".equals(progress)){
                                progressBar.setProgress(75);
                                tvProgress1.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress2.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress3.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress4.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress.setText(""+progress);
                            }else if ("100%".equals(progress)){
                                progressBar.setProgress(100);
                                tvProgress1.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress2.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress3.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress4.setTextColor(getResources().getColor(R.color.colorFD9));
                                tvProgress.setText(""+progress);
                            }else if ("0%".equals(progress)){
                                tvProgress1.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress2.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress3.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress4.setTextColor(getResources().getColor(R.color.colorWhite));
                                tvProgress.setText(""+progress);
                            }

                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 获取文字提示
     */
    private void getTips() {
        OkGo.post(Api.TEXT_TIPS)
                .execute(new JsonCallBack() {

                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        TextTipsBean textTipsBean = gson.fromJson(json, TextTipsBean.class);
                        if ("200".equals(textTipsBean.getCode())) {
//                            tvText1.setText(textTipsBean.getText().getText1());
//                            tvText1_1.setText(textTipsBean.getText().getText1_1());
//                            tvText1_2.setText(textTipsBean.getText().getText1_2());
                            tvText1.setText(textTipsBean.getText().getText1().replace("\\n","\n"));
                            tvText1_1.setText(textTipsBean.getText().getText1_1().replace("\\n","\n"));
                            tvApply.setText(textTipsBean.getText().getText1_2().replace("\\n","\n"));
                            //将钱包账单的文字保存起来，账单列表会用到
                            SPUtils.getInstance().put("text7_4", textTipsBean.getText().getText7_4().replace("\\n","\n"));

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
        String stringValue = "";
        OkGo.post(Api.VERIFYSTEP)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .params("currentStep", stringValue)
                .execute(new JsonCallBack() {
                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                        ivBorrow.setEnabled(false);
                    }

                    @Override
                    public void onResponse(String json) {
                        ivBorrow.setEnabled(true);
                        Gson gson = new Gson();
                        StepInfoBean stepInfoBean = gson.fromJson(json, StepInfoBean.class);
                        if ("200".equals(stepInfoBean.getCode())) {
                            String nextStep = stepInfoBean.getData().getFirstStep();
                            Log.d("MineFragment", "firstStep为：" + nextStep);
                            if ("FaceAuth".equals(nextStep)) {
                                Intent intent = new Intent(getContext(), CameraStepActivity.class);
                                startActivity(intent);
                            } else if ("IDAuth".equals(nextStep)) {
                                Intent intent = new Intent(getContext(), IDAuthActivity.class);
                                startActivity(intent);
                            } else if ("BasicInfo".equals(nextStep)) {
                                Intent intent = new Intent(getContext(), NewBaseInforActivity.class);
                                startActivity(intent);
                            } else if ("MountVerify".equals(nextStep)) {
                                Intent intent = new Intent(getContext(), AuthenticationActivity.class);
                                startActivity(intent);
                            } else if ("BindCard".equals(nextStep)) {
                                Intent intent = new Intent(getContext(), BankActivity.class);
                                startActivity(intent);
                            } else if ("PaymentSelect".equals(nextStep)) {
                                Intent intent = new Intent(getContext(), NewPayActivity.class);
                                startActivity(intent);
                            } else if ("Finish".equals(nextStep)) {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                                SPUtils.getInstance().put("is_vip", true);
                            } else if ("Wallet".equals(nextStep)) {
                                Intent intent = new Intent(getContext(), WalletActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 获取所有的金钱数据
     */
    private void getAllData() {
        OkGo.post(Api.HOME_ALL_DATA)
                .execute(new JsonCallBack() {
                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        AllDataBean allDataBean = gson.fromJson(json, AllDataBean.class);
                        ToastUtils.showShort(allDataBean.getMsg());
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

}

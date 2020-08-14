package com.sup.superwallet.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.facebook.appevents.AppEventsLogger;
import com.sup.superwallet.bean.HomeJsonBean;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.fragment.MineFragment;
import com.sup.superwallet.utils.BottomDialog;
import com.sup.superwallet.utils.WheelView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.sup.superwallet.R;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.JsonBean;
import com.sup.superwallet.bean.PayBean;
import com.sup.superwallet.bean.PayDataBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.bean.ViewFlipperBean;
import com.sup.superwallet.interfaces.OnCountDownTimerListener;
import com.sup.superwallet.utils.CommonCallback;
import com.sup.superwallet.utils.CountDownTimerUtils;
import com.sup.superwallet.utils.CountDownTimerUtils90;
import com.sup.superwallet.utils.JsonCallBack;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.referral.util.BranchEvent;

public class PayActivity extends BaseActivity implements PaymentResultListener {

    @BindView(R.id.tv_text7)
    TextView tvText7;
    @BindView(R.id.tv_text7_1)
    TextView tvText7_1;
    @BindView(R.id.tv_text7_2)
    TextView tvText7_2;
    @BindView(R.id.messsage_viewflipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.ll_pay_money_1)
    LinearLayout payMoney1;
    @BindView(R.id.tv_amount_1)
    TextView tvAmount1;
    @BindView(R.id.tv_interest_1)
    TextView tvInterest1;
    @BindView(R.id.tv_cycle_1)
    TextView tvCycle1;

    @BindView(R.id.cb_agree)
    TextView cbAgree;
    @BindView(R.id.tv_protocal)
    TextView tvProtocal;

    @BindView(R.id.ll_pay_money_2)
    LinearLayout payMoney2;
    @BindView(R.id.tv_amount_2)
    TextView tvAmount2;
    @BindView(R.id.tv_interest_2)
    TextView tvInterest2;
    @BindView(R.id.tv_cycle_2)
    TextView tvCycle2;

    @BindView(R.id.ll_pay_money_3)
    LinearLayout payMoney3;
    @BindView(R.id.tv_amount_3)
    TextView tvAmount3;
    @BindView(R.id.tv_interest_3)
    TextView tvInterest3;
    @BindView(R.id.tv_cycle_3)
    TextView tvCycle3;

    @BindView(R.id.ll_pay_money_4)
    LinearLayout payMoney4;
    @BindView(R.id.tv_amount_4)
    TextView tvAmount4;
    @BindView(R.id.tv_interest_4)
    TextView tvInterest4;
    @BindView(R.id.tv_cycle_4)
    TextView tvCycle4;

    @BindView(R.id.ll_pay_money_5)
    LinearLayout payMoney5;
    @BindView(R.id.tv_amount_5)
    TextView tvAmount5;
    @BindView(R.id.tv_interest_5)
    TextView tvInterest5;
    @BindView(R.id.tv_cycle_5)
    TextView tvCycle5;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.ll_submit)
    LinearLayout llSumbit;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;

    @BindView(R.id.tv_pay_num)
    TextView tvPayNum;


    private String payAmount; //支付的4个参数
    private String amount;
    private String orderId;
    private String day; //天数
    private boolean isSel;


    @Override
    protected int getLayout() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getTips();
        getHomeData();
        getPayData();

    }

    @Override
    protected void initListener() {

        tvProtocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayActivity.this, WebActivity.class);
                intent.putExtra("url", Api.PAY_PROTOCAL);
                intent.putExtra("title", "Payment Agreement");
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Drawable drawableLeft = getResources().getDrawable(
                R.drawable.ic_login_sel);
        final Drawable drawable = getResources().getDrawable(
                R.drawable.ic_yellow_nor);
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

        llSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSel) {
                    pay();
                    // payDialog();
                } else {
                    ToastUtils.showShort("Please agree to the payment agreement！");
                    return;
                }
            }
        });


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onPaymentSuccess(String s) {

        OkGo.<JsonBean>post(Api.PAY_RESULT)
                .params("order_number", orderId)
                .params("pay_channel", "razorpay")
                .params("pay_msg", "SUCCESS")
                .params("pay_RZToken", s)
                .params("pay_result", "SUCCESS")//pay_result   支付结果    1 是成功   2 不
                .execute(new CommonCallback<JsonBean>(PayActivity.this) {
                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        //配置branch
                        new BranchEvent("PaySuccess")
                                .addCustomDataProperty("uid", SPUtils.getInstance().getInt("uid") + "")
                                .addCustomDataProperty("orderid", orderId)
                                .addCustomDataProperty("mobile", SPUtils.getInstance().getString("mobile"))
                                .addCustomDataProperty("amount", amount + "")
                                .addCustomDataProperty("exorderid", "default")
                                .setCustomerEventAlias("PaySuccess")
                                .logEvent(PayActivity.this);
                        //配置facebook
                        AppEventsLogger logger = AppEventsLogger.newLogger(PayActivity.this);
                        Bundle bundle = new Bundle();
                        bundle.putString("orderid", orderId);
                        bundle.putString("channel", "razorpay");
                        String newAmount = amount.substring(1, amount.length());
                        logger.logPurchase(BigDecimal.valueOf(Integer.parseInt(newAmount)), Currency.getInstance("INR"), bundle);
                        creatDialog();
                        SPUtils.getInstance().put("is_vip", true);
                    }

                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }
                });

    }

    @Override
    public void onPaymentError(int i, String s) {

        OkGo.<JsonBean>post(Api.PAY_RESULT)
                .params("order_number", orderId)
                .params("pay_channel", "razorpay")
                .params("pay_msg", "CANCELL")
                .params("pay_RZToken", s)
                .params("pay_result", "CANCELL")//pay_result   支付结果    1 是成功   2 不
                .execute(new CommonCallback<JsonBean>(PayActivity.this) {
                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        ToastUtils.showShort(s);
                        creatDialog1();

                    }

                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }
                });

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
                    }

                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        TextTipsBean textTipsBean = gson.fromJson(json, TextTipsBean.class);
                        if ("200".equals(textTipsBean.getCode())) {
                            tvText7.setText(textTipsBean.getText().getText7());
                            tvText7_1.setText(textTipsBean.getText().getText7_1());
                            tvText7_2.setText(textTipsBean.getText().getText7_2());
                            SPUtils.getInstance().put("text8", textTipsBean.getText().getText8());
                            SPUtils.getInstance().put("text9", textTipsBean.getText().getText9());

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
     * 设置文字滚动
     */
    private void getHomeData() {
        OkGo.post(Api.HOME_DATA)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .execute(new JsonCallBack() {
                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        HomeJsonBean homeJsonBean = gson.fromJson(json, HomeJsonBean.class);
                        if ("200".equals(homeJsonBean.getCode())) {
                            //将金钱数存入缓存
                            List<String> data = new ArrayList<>();
                            Animation anim_in_left = AnimationUtils.loadAnimation(PayActivity.this, R.anim.slide_in_left);//左进
                            Animation anim_out_right = AnimationUtils.loadAnimation(PayActivity.this, R.anim.slide_out_right);//右出
                            viewFlipper.setInAnimation(anim_in_left);//添加进入动画效果
                            viewFlipper.setOutAnimation(anim_out_right);//添加退出动画效果

                            for (int i = 0; i < homeJsonBean.getData().getLoan_log().size(); i++) {
                                data.add(homeJsonBean.getData().getLoan_log().get(i));
                            }
//
                            for (int i = 0; i < data.size(); i++) {//遍历图片资源
                                TextView textView = new TextView(PayActivity.this);//创建ImageView对象
                                textView.setText(data.get(i));//将遍历的图片保存在ImageView中
                                textView.setTextSize(13);
                                textView.setTextColor(getResources().getColor(R.color.colorBlack));
                                viewFlipper.addView(textView);//  //加载图片
                            }


                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 请求支付接口的基本数据
     */
    private void getPayData() {
        OkGo.post(Api.GET_PAY_INFO)
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
                        PayDataBean payDataBean = gson.fromJson(json, PayDataBean.class);
                        if ("200".equals(payDataBean.getCode())) {
                            Log.d("pay", "请求支付数据成功");
                            int size = payDataBean.getLoan_info().size();
                            if (size == 2) {
                                tvAmount1.setText(payDataBean.getLoan_info().get(0).getLoan_amount());
                                tvAmount2.setText(payDataBean.getLoan_info().get(1).getLoan_amount());
                                tvInterest1.setText("Interest:\t" + payDataBean.getLoan_info().get(0).getShow_amount());
                                tvInterest2.setText("Interest:\t" + payDataBean.getLoan_info().get(1).getShow_amount());
                                tvCycle1.setText("Cycle:\t" + payDataBean.getLoan_info().get(0).getLoan_time());
                                tvCycle2.setText("Cycle:\t" + payDataBean.getLoan_info().get(1).getLoan_time());
                                payMoney3.setVisibility(View.GONE);
                                payMoney4.setVisibility(View.GONE);
                                payMoney5.setVisibility(View.GONE);
                            } else if (size == 3) {
                                tvAmount1.setText(payDataBean.getLoan_info().get(0).getLoan_amount());
                                tvAmount2.setText(payDataBean.getLoan_info().get(1).getLoan_amount());
                                tvAmount3.setText(payDataBean.getLoan_info().get(2).getLoan_amount());
                                tvInterest1.setText("Interest:\t" + payDataBean.getLoan_info().get(0).getShow_amount());
                                tvInterest2.setText("Interest:\t" + payDataBean.getLoan_info().get(1).getShow_amount());
                                tvInterest3.setText("Interest:\t" + payDataBean.getLoan_info().get(2).getShow_amount());
                                tvCycle1.setText("Cycle:\t" + payDataBean.getLoan_info().get(0).getLoan_time());
                                tvCycle2.setText("Cycle:\t" + payDataBean.getLoan_info().get(1).getLoan_time());
                                tvCycle3.setText("Cycle:\t" + payDataBean.getLoan_info().get(2).getLoan_time());
                                payMoney4.setVisibility(View.GONE);
                                payMoney5.setVisibility(View.GONE);
                            } else if (size == 4) {
                                tvAmount1.setText(payDataBean.getLoan_info().get(0).getLoan_amount());
                                tvAmount2.setText(payDataBean.getLoan_info().get(1).getLoan_amount());
                                tvAmount3.setText(payDataBean.getLoan_info().get(2).getLoan_amount());
                                tvAmount4.setText(payDataBean.getLoan_info().get(3).getLoan_amount());
                                tvInterest1.setText("Interest:\t" + payDataBean.getLoan_info().get(0).getShow_amount());
                                tvInterest2.setText("Interest:\t" + payDataBean.getLoan_info().get(1).getShow_amount());
                                tvInterest3.setText("Interest:\t" + payDataBean.getLoan_info().get(2).getShow_amount());
                                tvInterest4.setText("Interest:\t" + payDataBean.getLoan_info().get(3).getShow_amount());
                                tvCycle1.setText("Cycle:\t" + payDataBean.getLoan_info().get(0).getLoan_time());
                                tvCycle2.setText("Cycle:\t" + payDataBean.getLoan_info().get(1).getLoan_time());
                                tvCycle3.setText("Cycle:\t" + payDataBean.getLoan_info().get(2).getLoan_time());
                                tvCycle4.setText("Cycle:\t" + payDataBean.getLoan_info().get(3).getLoan_time());
                                payMoney5.setVisibility(View.GONE);
                            } else if (size == 5) {
                                tvAmount1.setText(payDataBean.getLoan_info().get(0).getLoan_amount());
                                tvAmount2.setText(payDataBean.getLoan_info().get(1).getLoan_amount());
                                tvAmount3.setText(payDataBean.getLoan_info().get(2).getLoan_amount());
                                tvAmount4.setText(payDataBean.getLoan_info().get(3).getLoan_amount());
                                tvAmount5.setText(payDataBean.getLoan_info().get(4).getLoan_amount());
                                tvInterest1.setText("Interest:\t" + payDataBean.getLoan_info().get(0).getShow_amount());
                                tvInterest2.setText("Interest:\t" + payDataBean.getLoan_info().get(1).getShow_amount());
                                tvInterest3.setText("Interest:\t" + payDataBean.getLoan_info().get(2).getShow_amount());
                                tvInterest4.setText("Interest:\t" + payDataBean.getLoan_info().get(3).getShow_amount());
                                tvInterest5.setText("Interest:\t" + payDataBean.getLoan_info().get(4).getShow_amount());
                                tvCycle1.setText("Cycle:\t" + payDataBean.getLoan_info().get(0).getLoan_time());
                                tvCycle2.setText("Cycle:\t" + payDataBean.getLoan_info().get(1).getLoan_time());
                                tvCycle3.setText("Cycle:\t" + payDataBean.getLoan_info().get(2).getLoan_time());
                                tvCycle4.setText("Cycle:\t" + payDataBean.getLoan_info().get(3).getLoan_time());
                                tvCycle5.setText("Cycle:\t" + payDataBean.getLoan_info().get(4).getLoan_time());
                            }

                            //默认选中最小的数据
                            payAmount = payDataBean.getLoan_info().get(0).getAmount();
                            amount = payDataBean.getLoan_info().get(0).getLoan_amount();
                            day = payDataBean.getLoan_info().get(0).getLoan_time();
                            tvPayNum.setText(payDataBean.getLoan_info().get(0).getShow_amount());
                            tvAmount1.setTextColor(getResources().getColor(R.color.colorWhite));
                            tvInterest1.setTextColor(getResources().getColor(R.color.colorWhite));
                            tvCycle1.setTextColor(getResources().getColor(R.color.colorWhite));

                            payMoney1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    payMoney1.setBackgroundResource(R.drawable.shape_gradient_10);
                                    payMoney2.setBackgroundResource(R.color.colorWhite);
                                    payMoney3.setBackgroundResource(R.color.colorWhite);
                                    payMoney4.setBackgroundResource(R.color.colorWhite);
                                    payMoney5.setBackgroundResource(R.color.colorWhite);

                                    tvPayNum.setText(payDataBean.getLoan_info().get(0).getShow_amount());

                                    tvAmount1.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvInterest1.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvCycle1.setTextColor(getResources().getColor(R.color.colorWhite));

                                    tvAmount2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle2.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount3.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest3.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle3.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount4.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest4.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle4.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount5.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest5.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle5.setTextColor(getResources().getColor(R.color.colorBlack));

                                    payAmount = payDataBean.getLoan_info().get(0).getAmount();
                                    amount = payDataBean.getLoan_info().get(0).getLoan_amount();
                                    day = payDataBean.getLoan_info().get(0).getLoan_time();


                                }
                            });

                            payMoney2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    payMoney2.setBackgroundResource(R.drawable.shape_gradient_10);
                                    payMoney1.setBackgroundResource(R.color.colorWhite);
                                    payMoney3.setBackgroundResource(R.color.colorWhite);
                                    payMoney4.setBackgroundResource(R.color.colorWhite);
                                    payMoney5.setBackgroundResource(R.color.colorWhite);

                                    tvPayNum.setText(payDataBean.getLoan_info().get(1).getShow_amount());

                                    tvAmount2.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvInterest2.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvCycle2.setTextColor(getResources().getColor(R.color.colorWhite));

                                    tvAmount1.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest1.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle1.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount3.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest3.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle3.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount4.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest4.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle4.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount5.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest5.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle5.setTextColor(getResources().getColor(R.color.colorBlack));

                                    payAmount = payDataBean.getLoan_info().get(1).getAmount();
                                    amount = payDataBean.getLoan_info().get(1).getLoan_amount();
                                    day = payDataBean.getLoan_info().get(1).getLoan_time();

                                }
                            });

                            payMoney3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    payMoney3.setBackgroundResource(R.drawable.shape_gradient_10);
                                    payMoney1.setBackgroundResource(R.color.colorWhite);
                                    payMoney2.setBackgroundResource(R.color.colorWhite);
                                    payMoney4.setBackgroundResource(R.color.colorWhite);
                                    payMoney5.setBackgroundResource(R.color.colorWhite);

                                    tvPayNum.setText(payDataBean.getLoan_info().get(2).getShow_amount());

                                    tvAmount3.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvInterest3.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvCycle3.setTextColor(getResources().getColor(R.color.colorWhite));

                                    tvAmount2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle2.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount1.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest1.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle1.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount4.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest4.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle4.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount5.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest5.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle5.setTextColor(getResources().getColor(R.color.colorBlack));

                                    payAmount = payDataBean.getLoan_info().get(2).getAmount();
                                    amount = payDataBean.getLoan_info().get(2).getLoan_amount();
                                    day = payDataBean.getLoan_info().get(2).getLoan_time();

                                }
                            });

                            payMoney4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    payMoney4.setBackgroundResource(R.drawable.shape_gradient_10);
                                    payMoney1.setBackgroundResource(R.color.colorWhite);
                                    payMoney2.setBackgroundResource(R.color.colorWhite);
                                    payMoney3.setBackgroundResource(R.color.colorWhite);
                                    payMoney5.setBackgroundResource(R.color.colorWhite);

                                    tvPayNum.setText(payDataBean.getLoan_info().get(3).getShow_amount());

                                    tvAmount4.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvInterest4.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvCycle4.setTextColor(getResources().getColor(R.color.colorWhite));

                                    tvAmount2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle2.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount3.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest3.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle3.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount1.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest1.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle1.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount5.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest5.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle5.setTextColor(getResources().getColor(R.color.colorBlack));

                                    payAmount = payDataBean.getLoan_info().get(3).getAmount();
                                    amount = payDataBean.getLoan_info().get(3).getLoan_amount();
                                    day = payDataBean.getLoan_info().get(3).getLoan_time();

                                }
                            });

                            payMoney5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    payMoney5.setBackgroundResource(R.drawable.shape_gradient_10);
                                    payMoney1.setBackgroundResource(R.color.colorWhite);
                                    payMoney2.setBackgroundResource(R.color.colorWhite);
                                    payMoney3.setBackgroundResource(R.color.colorWhite);
                                    payMoney4.setBackgroundResource(R.color.colorWhite);
                                    tvPayNum.setText(payDataBean.getLoan_info().get(4).getShow_amount());

                                    tvAmount5.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvInterest5.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvCycle5.setTextColor(getResources().getColor(R.color.colorWhite));

                                    tvAmount2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle2.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount3.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest3.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle3.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount4.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest4.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle4.setTextColor(getResources().getColor(R.color.colorBlack));

                                    tvAmount1.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvInterest1.setTextColor(getResources().getColor(R.color.colorBlack));
                                    tvCycle1.setTextColor(getResources().getColor(R.color.colorBlack));


                                    payAmount = payDataBean.getLoan_info().get(4).getAmount();
                                    amount = payDataBean.getLoan_info().get(4).getLoan_amount();
                                    day = payDataBean.getLoan_info().get(4).getLoan_time();

                                }
                            });


                        } else {
                            ToastUtils.showShort(payDataBean.getCode());
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });

    }

    //支付功能
    private void pay() {
        OkGo.post(Api.GET_PAY_DATA)
                .params("orderLoanType", 1 + "")
                .params("orderLoanAmount", amount) //借款金额
                .params("orderAmount", payAmount) //支付的金额
                .params("orderLoanCycle", day)
                .execute(new JsonCallBack() {

                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                        llSumbit.setEnabled(false);

                    }

                    @Override
                    public void onResponse(String json) {
                        llSumbit.setEnabled(true);
                        Gson gson = new Gson();
                        PayBean payBean = gson.fromJson(json, PayBean.class);
                        if ("200".equals(payBean.getCode())) {

                            Checkout checkout = new Checkout();
                            checkout.setKeyID(payBean.getOrderData().getAppId());
                            orderId = payBean.getOrderData().getOrderNumber();

                            /**
                             * Set your logo here
                             */
                            checkout.setImage(R.mipmap.ic_launcher);

                            /**
                             * Reference to current activity
                             */
//                                final Activity activity = this;
                            /**
                             * Pass your payment options to the Razorpay Checkout as a JSONObject
                             */
                            try {
                                JSONObject options = new JSONObject();
                                options.put("name", payBean.getOrderData().getName());
                                options.put("description", payBean.getOrderData().getDescription());
                                options.put("image", payBean.getOrderData().getImage());
                                options.put("order_id", payBean.getOrderData().getOrderNumber());
                                options.put("currency", payBean.getOrderData().getOrderCurrency());

                                /**
                                 * Amount is always passed in currency subunits
                                 * Eg: "500" = INR 5.00
                                 */

                                options.put("amount", payBean.getOrderData().getOrderAmount());
                                checkout.open(PayActivity.this, options);
                            } catch (Exception e) {
                                Log.e("StepPayActivity", "Error in starting Razorpay Checkout", e);
                            }
                        } else {
                            ToastUtils.showShort(payBean.getMsg());
                            Log.d("PayActivity", "信息为：" + payBean.getMsg());
                        }

                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 支付成功所对应的弹窗
     */

    private void creatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
        View v = LayoutInflater.from(PayActivity.this).inflate(R.layout.dialog_pay_result, null, false);
        ViewHolder viewHolder = new ViewHolder(v);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        viewHolder.tvText8.setText(SPUtils.getInstance().getString("text9"));
        viewHolder.tvSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //前往代超页面
                SPUtils.getInstance().put("isPay", true);
                step();
                //进行网络请求，从后台控制步骤
                Intent intent = new Intent(PayActivity.this, MainActivity.class);
                startActivity(intent);
                PayActivity.this.finish();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sumbit)
        TextView tvSumbit;
        @BindView(R.id.text1)
                TextView tvText8;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 支付失败所对应的弹窗
     */

    private void creatDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
        View v = LayoutInflater.from(PayActivity.this).inflate(R.layout.dialog_pay_result_1, null, false);
        ViewHolder1 viewHolder1 = new ViewHolder1(v);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        viewHolder1.tvText9.setText(SPUtils.getInstance().getString("text8"));
        viewHolder1.tvSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //前往代超页面
            }
        });
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sumbit)
        TextView tvSumbit;
        @BindView(R.id.text1)
        TextView tvText9;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


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
        String stringValue = "PaymentSelect";
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
//                            String nextStep = stepInfoBean.getData().getNextStep();
//                            Log.d("BankActivity", "nextStep为：" + nextStep);
//                            if ("IDAuth".equals(nextStep)) {
//                                Intent intent = new Intent(PayActivity.this, IDAuthActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else if ("BasicInfo".equals(nextStep)) {
//                                Intent intent = new Intent(PayActivity.this, NewBaseInforActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else if ("MountVerify".equals(nextStep)) {
//                                Intent intent = new Intent(PayActivity.this, AuthenticationActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else if ("BindCard".equals(nextStep)) {
//                                Intent intent = new Intent(PayActivity.this, BankActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else if ("PaymentSelect".equals(nextStep)) {
//                                Intent intent = new Intent(PayActivity.this, PayActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else if ("Finish".equals(nextStep)) {
//                                Intent intent = new Intent(PayActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                SPUtils.getInstance().put("is_vip", true);
//                                finish();
//                            }
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }


}

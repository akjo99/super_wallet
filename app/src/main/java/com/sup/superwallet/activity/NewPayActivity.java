package com.sup.superwallet.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.sup.superwallet.R;
import com.sup.superwallet.adapter.MoneyAdapter;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.HomeJsonBean;
import com.sup.superwallet.bean.JsonBean;
import com.sup.superwallet.bean.PayBean;
import com.sup.superwallet.bean.PayDataBean;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.interfaces.OnCountDownTimerListener;
import com.sup.superwallet.utils.CommonCallback;
import com.sup.superwallet.utils.CountDownTimerUtils90;
import com.sup.superwallet.utils.JsonCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.referral.util.BranchEvent;

public class NewPayActivity extends BaseActivity implements PaymentResultListener {
    @BindView(R.id.rc_pay_money)
    RecyclerView rcPayMoney;
    @BindView(R.id.tv_pay_num)
    TextView tvPayNum;
    @BindView(R.id.tv_text8)
    TextView tvText8;
    @BindView(R.id.tv_text8_1)
    TextView tvText8_1;
    @BindView(R.id.tv_text8_2)
    TextView tvText8_2;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.messsage_viewflipper)
    ViewFlipper viewFlipper;

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;

    @BindView(R.id.cb_agree)
    TextView cbAgree;
    @BindView(R.id.tv_protocal)
    TextView tvProtocal;

    @BindView(R.id.ll_protocol)
    LinearLayout llProtocal;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;
    private String showAmountNum;

    @BindView(R.id.tv_timing)
    TextView tvTiming;


    private CountDownTimerUtils90 instance;


    private String payAmount; //支付的4个参数
    private String amount;
    private String orderId;
    private String day; //天数
    private boolean isSel;

    long millisInFuture = 5400000;


    private MoneyAdapter moneyAdapter;
    private List<PayDataBean.LoanInfoBean> loanInfoBeans = new ArrayList<>();
    private int paySize;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {

                for (int i = 0; i < paySize; i++) {
                    LinearLayout ll = (LinearLayout) moneyAdapter.getViewByPosition(rcPayMoney, i, R.id.ll_pay_money);
                    TextView text1 = (TextView) moneyAdapter.getViewByPosition(rcPayMoney, i, R.id.tv_amount);
                    TextView text2 = (TextView) moneyAdapter.getViewByPosition(rcPayMoney, i, R.id.tv_interest);
                    if (i != msg.arg1) {
                        ll.setBackgroundResource(R.drawable.ic_amount_no_choose);
//                        text1.setTextColor(getResources().getColor(R.color.colorBlack));
//                        text2.setTextColor(getResources().getColor(R.color.colorBlack));
//                        text3.setTextColor(getResources().getColor(R.color.colorBlack));
                    } else {
                        ll.setBackgroundResource(R.drawable.amount_select);
//                        text1.setTextColor(getResources().getColor(R.color.colorWhite));
//                        text2.setTextColor(getResources().getColor(R.color.colorWhite));
//                        text3.setTextColor(getResources().getColor(R.color.colorWhite));
                        tvPayNum.setText(showAmountNum);
                    }
                }
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_pay_new;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getTips();
        rcPayMoney.setLayoutManager(new LinearLayoutManager(this));
        moneyAdapter = new MoneyAdapter(loanInfoBeans);
        rcPayMoney.setAdapter(moneyAdapter);
    }

    @Override
    protected void initListener() {

        tvProtocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewPayActivity.this, WebActivity.class);
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

        llSubmit.setOnClickListener(new View.OnClickListener() {
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
    protected void onResume() {
        super.onResume();
        instance = CountDownTimerUtils90.getInstance(millisInFuture);


        instance.setOnCountDownTimerListener(new OnCountDownTimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                millisInFuture = millisUntilFinished;
                long hour = millisUntilFinished / (1000 * 60 * 60);
                long min = (millisUntilFinished - hour * (1000 * 60 * 60)) / (1000 * 60);
                long sec = (millisUntilFinished - hour * (1000 * 60 * 60) - min * (1000 * 60)) / 1000;
                tvTiming.setText("0" + hour + ":\t" + min + ":\t" + sec);
            }

            @Override
            public void onFinish() {
                tvTiming.setText("no time left");
            }
        });
        instance.startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.stopTimer();
    }

    @Override
    protected void initData() {
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
                            Animation anim_in_left = AnimationUtils.loadAnimation(NewPayActivity.this, R.anim.slide_in_left);//左进
                            Animation anim_out_right = AnimationUtils.loadAnimation(NewPayActivity.this, R.anim.slide_out_right);//右出
                            viewFlipper.setInAnimation(anim_in_left);//添加进入动画效果
                            viewFlipper.setOutAnimation(anim_out_right);//添加退出动画效果

                            for (int i = 0; i < homeJsonBean.getData().getLoan_log().size(); i++) {
                                data.add(homeJsonBean.getData().getLoan_log().get(i));
                            }
//
                            for (int i = 0; i < data.size(); i++) {//遍历图片资源
                                TextView textView = new TextView(NewPayActivity.this);//创建ImageView对象
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
                            moneyAdapter.setNewData(payDataBean.getLoan_info());
                            paySize = payDataBean.getLoan_info().size();
                            if (1==payDataBean.getIs_showAgree()){
                                llProtocal.setVisibility(View.VISIBLE);
                            }else if (2==payDataBean.getIs_showAgree()){
                                llProtocal.setVisibility(View.INVISIBLE);
                            }

                            if (1==payDataBean.getIs_showTime()){
                                tvTiming.setVisibility(View.VISIBLE);
                            }else if (2==payDataBean.getIs_showTime()){
                                tvTiming.setVisibility(View.GONE);
                            }

                            //默认选中第一组数据
                            showAmountNum = payDataBean.getLoan_info().get(0).getShow_amount();
                            Message message = new Message();
                            message.what = 100;
                            message.arg1 = 0;
                            handler.sendMessage(message);
                            payAmount = payDataBean.getLoan_info().get(0).getAmount();
                            amount = payDataBean.getLoan_info().get(0).getLoan_amount();
                            day = payDataBean.getLoan_info().get(0).getLoan_time();

                            moneyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    showAmountNum = payDataBean.getLoan_info().get(position).getShow_amount();
                                    //借款金额
                                    payAmount = payDataBean.getLoan_info().get(position).getAmount();
                                    amount = payDataBean.getLoan_info().get(position).getLoan_amount();
                                    day = payDataBean.getLoan_info().get(position).getLoan_time();
                                    Message message = new Message();
                                    message.what = 100;
                                    message.arg1 = position;
                                    handler.sendMessage(message);
                                }
                            });
                        } else {
                            ToastUtils.showShort(payDataBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String json) {

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
                        multipleStatusView.showLoading();
                    }

                    @Override
                    public void onResponse(String json) {
                        getHomeData();
                        getPayData();
                        Gson gson = new Gson();
                        TextTipsBean textTipsBean = gson.fromJson(json, TextTipsBean.class);
                        if ("200".equals(textTipsBean.getCode())) {
                            tvText8.setText(textTipsBean.getText().getText8().replace("\\n","\n"));
                            tvText8_1.setText(textTipsBean.getText().getText8_1().replace("\\n","\n"));
                            tvText8_2.setText(textTipsBean.getText().getText8_2().replace("\\n","\n"));
                            SPUtils.getInstance().put("text8", textTipsBean.getText().getText8().replace("\\n","\n"));
                            SPUtils.getInstance().put("text9", textTipsBean.getText().getText9().replace("\\n","\n"));
                        } else {
                            ToastUtils.showShort(textTipsBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    @Override
    public void onPaymentSuccess(String s) {
        OkGo.<JsonBean>post(Api.PAY_RESULT)
                .params("order_number", orderId)
                .params("pay_channel", "razorpay")
                .params("pay_msg", "SUCCESS")
                .params("pay_RZToken", s)
                .params("pay_result", "SUCCESS")//pay_result   支付结果    1 是成功   2 不
                .execute(new CommonCallback<JsonBean>(NewPayActivity.this) {
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
                                .logEvent(NewPayActivity.this);
                        //配置facebook
                        AppEventsLogger logger = AppEventsLogger.newLogger(NewPayActivity.this);
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
                .execute(new CommonCallback<JsonBean>(NewPayActivity.this) {
                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        ToastUtils.showShort(s);
                        creatDialog1();
                    }

                    @Override
                    public void onError(Response<JsonBean> response) {
                        super.onError(response);
                        creatDialog1();
                    }

                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }
                });

    }
    /**
     * 支付
     */
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
                        llSubmit.setEnabled(false);

                    }

                    @Override
                    public void onResponse(String json) {
                        llSubmit.setEnabled(true);
                        Gson gson = new Gson();
                        PayBean payBean = gson.fromJson(json, PayBean.class);
                        if ("200".equals(payBean.getCode())) {

                            Checkout checkout = new Checkout();
                            checkout.setKeyID(payBean.getOrderData().getAppId());
                            orderId = payBean.getOrderData().getOrderNumber();

                            /**
                             * Set your logo here
                             */
                            checkout.setImage(R.drawable.icon_logo);

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
                                checkout.open(NewPayActivity.this, options);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(NewPayActivity.this);
        View v = LayoutInflater.from(NewPayActivity.this).inflate(R.layout.dialog_pay_result, null, false);
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
                Intent intent = new Intent(NewPayActivity.this, MainActivity.class);
                startActivity(intent);
                NewPayActivity.this.finish();
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
        Log.d("NewPayActivity","failed");
        AlertDialog.Builder builder = new AlertDialog.Builder(NewPayActivity.this);
        View v = LayoutInflater.from(NewPayActivity.this).inflate(R.layout.dialog_pay_result_1, null, false);
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

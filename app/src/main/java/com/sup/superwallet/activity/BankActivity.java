package com.sup.superwallet.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.sup.superwallet.adapter.AuthenticationAdapter;
import com.sup.superwallet.bean.AuthBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class BankActivity extends BaseActivity {

    @BindView(R.id.tv_sumbit)
    TextView tvSubmit;
    @BindView(R.id.tv_text6)
    TextView tvText6;
    @BindView(R.id.tv_text6_1)
    TextView tvText6_1;
    @BindView(R.id.rc_base_info)
    RecyclerView rcBaseInfo;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;
    private AuthenticationAdapter authenticationAdapter;
    private List<AuthBean.DataArrBean> dataArrBeans = new ArrayList<>();
    //定义一个保存key的集合
    private List<String> keys = new ArrayList<>();
    //定义一个保存错误信息的结合
    private List<String> errMsg = new ArrayList<>();
    //定义一个保存类型的集合
    private List<Integer> type = new ArrayList<>();
    //定义一个保存已甜文本信息的集合
    private List<String> bankInfo = new ArrayList<>();
    private BottomDialog bottomDialog;
    private String value;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                String str = (String) msg.obj;
                if (0==msg.arg2){ //textview显示文字
                    TextView tv1 = (TextView) authenticationAdapter.getViewByPosition(rcBaseInfo,msg.arg1,R.id.tv_email);
                    if (!TextUtils.isEmpty(bankInfo.get(msg.arg1))){
                        tv1.setText(bankInfo.get(msg.arg1));
                    }else {
                        tv1.setHint(str);
                    }
                }else {
                    EditText ed1 = (EditText) authenticationAdapter.getViewByPosition(rcBaseInfo, msg.arg1, R.id.ed_email);
                    if (!TextUtils.isEmpty(bankInfo.get(msg.arg1))){
                        ed1.setText(bankInfo.get(msg.arg1));
                    }else {
                        ed1.setHint(str);
                    }
                    if (2 == msg.arg2) {
                        ed1.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                }
            }else if (msg.what ==200 ){
                String str = (String) msg.obj;
                TextView tv1 = (TextView) authenticationAdapter.getViewByPosition(rcBaseInfo,msg.arg1,R.id.tv_email);
                tv1.setText(str);
            }
        }
    };
    @Override
    protected int getLayout() {
        return R.layout.activity_bank;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getTips();
        rcBaseInfo.setLayoutManager(new LinearLayoutManager(this));
        authenticationAdapter = new AuthenticationAdapter(dataArrBeans);
        rcBaseInfo.setAdapter(authenticationAdapter);
    }

    @Override
    protected void initListener() {

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> map = new HashMap();
                //这段代码谁也别想看懂
                for (int i=0;i<keys.size();i++){
                    if (type.get(i)==0){
                        TextView tv = (TextView) authenticationAdapter.getViewByPosition(rcBaseInfo,i,R.id.tv_email);
                        if (TextUtils.isEmpty(tv.getText().toString())){
                            ToastUtils.showShort(errMsg.get(i));
                            return;
                        }else {
                            map.put(keys.get(i), tv.getText().toString());
                        }
                    }else {
                        EditText ed = (EditText) authenticationAdapter.getViewByPosition(rcBaseInfo, i, R.id.ed_email);
                        if (TextUtils.isEmpty(ed.getText().toString())) {
                            ToastUtils.showShort(errMsg.get(i));
                            return;
                        } else {
                            map.put(keys.get(i), ed.getText().toString());
                        }
                    }
                }
                sendInfo(map);

            }
        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
                        getBaseInfo();
                        Gson gson = new Gson();
                        TextTipsBean textTipsBean = gson.fromJson(json, TextTipsBean.class);
                        if ("200".equals(textTipsBean.getCode())) {
                            tvText6.setText(textTipsBean.getText().getText6().replace("\\n","\n"));
                            tvText6_1.setText(textTipsBean.getText().getText6_1().replace("\\n","\n"));
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
     * 调取基本信息数据
     */
    private void getBaseInfo() {
        OkGo.post(Api.BANK_CARD_INFO)
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
                        AuthBean authBean = gson.fromJson(json,AuthBean.class);
                        if ("200".equals(authBean.getCode())){
                            authenticationAdapter.setNewData(authBean.getDataArr());
                            for (int i=0;i<authBean.getDataArr().size();i++){
                                keys.add(authBean.getDataArr().get(i).getKey());
                                errMsg.add(authBean.getDataArr().get(i).getErrMsg());
                                type.add(Integer.parseInt(authBean.getDataArr().get(i).getInputType()));
                                bankInfo.add(authBean.getDataArr().get(i).getName());
                                Message message = new Message();
                                message.obj = authBean.getDataArr().get(i).getPlaceHolder();
                                message.arg1 = i;
                                message.arg2 = Integer.parseInt(authBean.getDataArr().get(i).getInputType());
                                message.what = 100;
                                handler.sendMessage(message);
                            }
                            authenticationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    if ("0".equals(authBean.getDataArr().get(position).getInputType())){
                                        List<String> sex = new ArrayList<>();
                                        for (int i=0;i<authBean.getDataArr().get(position).getDataSource().size();i++){
                                            sex.add(authBean.getDataArr().get(position).getDataSource().get(i).getContent());
                                        }
                                        limits(sex,position);
                                    }
                                }
                            });

                        }
                    }
                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 弹出选择框
     */
    private void limits(List<String> limits,int position) {
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
                for (int i=0;i<limits.size();i++){
                    if (limits.get(i).equals(nTimeLimit)) {
                        value = limits.get(i);
                        break;
                    }
                }
                Message message = new Message();
                message.what = 200;
                message.obj = value;
                message.arg1 = position;
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
     * 向后台提交银行卡信息
     */
    private void sendInfo(Map<String,String> map) {

        OkGo.post(Api.UPDATE_BANK_INFO)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .params(map)
                .execute(new JsonCallBack() {

                    @Override
                    public void onStart(Request request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        SubmitInfoBean submitInfoBean = gson.fromJson(json, SubmitInfoBean.class);
                        if ("200".equals(submitInfoBean.getCode())) {
                            ToastUtils.showShort("Submit Success！");
                            //进行后台控制进程
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
        String stringValue = "BindCard";
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
                                Intent intent = new Intent(BankActivity.this, CameraStepActivity.class);
                                startActivity(intent);
                            } else if ("IDAuth".equals(nextStep)) {
                                Intent intent = new Intent(BankActivity.this, IDAuthActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("BasicInfo".equals(nextStep)) {
                                Intent intent = new Intent(BankActivity.this, NewBaseInforActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("MountVerify".equals(nextStep)) {
                                Intent intent = new Intent(BankActivity.this, AuthenticationActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("BindCard".equals(nextStep)) {
                                Intent intent = new Intent(BankActivity.this, BankActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("PaymentSelect".equals(nextStep)) {
                                Intent intent = new Intent(BankActivity.this, NewPayActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("Finish".equals(nextStep)) {
                                Intent intent = new Intent(BankActivity.this, MainActivity.class);
                                startActivity(intent);
                                SPUtils.getInstance().put("is_vip", true);
                                finish();
                            }else if ("Wallet".equals(nextStep)){
                                Intent intent = new Intent(BankActivity.this,WalletActivity.class);
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



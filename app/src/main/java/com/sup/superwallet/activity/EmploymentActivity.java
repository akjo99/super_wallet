package com.sup.superwallet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
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
import com.sup.superwallet.utils.Common;
import com.sup.superwallet.utils.JsonCallBack;
import com.sup.superwallet.utils.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EmploymentActivity extends BaseActivity {
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.iv_back)
    ImageView ivback;
    @BindView(R.id.tv_text4)
    TextView tvText4;
    @BindView(R.id.tv_text4_1)
    TextView tvText4_1;
    @BindView(R.id.tv_emp_type)
    TextView tvType;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.working_since)
    TextView tvWorkingSince;
    @BindView(R.id.tv_sumbit)
    TextView tvSumbit;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;
    private BottomDialog bottomDialog;
    private String workType;
    private String income;
    private String workingSince;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String str = (String) msg.obj;
                tvType.setText(str);
            } else if (msg.what == 2) {
                String str = (String) msg.obj;
                tvIncome.setText(str);
            } else if (msg.what == 3) {
                String str = (String) msg.obj;
                tvWorkingSince.setText(str);
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_employment;
    }

    public static void forward(Context context, int type) {
        Intent intent = new Intent(context, EmploymentActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitles.setText("Employment Information");
        getTips();
        getVerifyStepInfo();
    }

    @Override
    protected void initListener() {
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInfo();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_em_type, R.id.ll_uncome, R.id.ll_working})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_em_type:
                List<String> workType = new ArrayList<>();
                workType.add("Full Time");
                workType.add("Part Time");
                workType.add("Freeiancer");
                workType.add("No Work");
                limits(workType);
                break;
            case R.id.ll_uncome:
                List<String> income = new ArrayList<>();
                income.add("10,000~15,000");
                income.add("15,000~20,000");
                income.add("20,000~3,0000");
                income.add("More Than 30,000");
                getIncome(income);
                break;
            case R.id.ll_working:
//                List<String> workingSince = new ArrayList<>();
//                workingSince.add("2020");
//                workingSince.add("2019");
//                workingSince.add("2018");
//                workingSince.add("2017");
//                workingSince.add("2016");
//                workingSince.add("2015");
//                workingSince.add("2014");
//                workingSince.add("2013");
//                workingSince.add("2012");
//                workingSince.add("2011");
//                workingSince.add("2010");
//                workingSince.add("2009");
//                workingSince.add("2008");
//                workingSince.add("2007");
//                workingSince.add("2006");
//                workingSince.add("2005");
//                workingSince.add("2004");
//                workingSince.add("2003");
//                workingSince.add("2002");
//                workingSince.add("2001");
//                workingSince.add("2000");
//                getWorkSince(workingSince);
                //调用选择日期工具的界面
                pickData();
                break;

        }
    }

    /**
     * 工作性质
     *
     * @param limits
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
                if ("Full Time".equals(nTimeLimit)) {
                    workType = "Full Time";
                } else if ("Part Time".equals(nTimeLimit)) {
                    workType = "Part Time";
                } else if ("Freeiancer".equals(nTimeLimit)) {
                    workType = "Freeiancer";
                } else if ("No Work".equals(nTimeLimit)) {
                    workType = "No Work";
                }
                Message message = new Message();
                message.what = 1;
                message.obj = workType;
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
     * 收入状况
     */
    private void getIncome(List<String> limits){

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
                if ("10,000~15,000".equals(nTimeLimit)) {
                    income = "10,000~15,000";
                } else if ("15,000~20,000".equals(nTimeLimit)) {
                    income = "15,000~20,000";
                } else if ("20,000~3,0000".equals(nTimeLimit)) {
                    income = "20,000~3,0000";
                } else if ("More Than 30,000".equals(nTimeLimit)) {
                    income = "More Than 30,000";
                }
                Message message = new Message();
                message.what = 2;
                message.obj = income;
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
     * 参加工作时间
     */

    private void getWorkSince(List<String> limits){

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
                if ("2020".equals(nTimeLimit)) {
                    workingSince = "2020";
                } else if ("2019".equals(nTimeLimit)) {
                    workingSince = "2019";
                } else if ("2018".equals(nTimeLimit)) {
                    workingSince = "2018";
                }  else if ("2017".equals(nTimeLimit)) {
                    workingSince = "2017";
                }else if ("2016".equals(nTimeLimit)) {
                    workingSince = "2016";
                }else if ("2015".equals(nTimeLimit)) {
                    workingSince = "2015";
                }else if ("2014".equals(nTimeLimit)) {
                    workingSince = "2014";
                }else if ("2013".equals(nTimeLimit)) {
                    workingSince = "2013";
                }else if ("2012".equals(nTimeLimit)) {
                    workingSince = "2012";
                }else if ("2011".equals(nTimeLimit)) {
                    workingSince = "2011";
                }else if ("2010".equals(nTimeLimit)) {
                    workingSince = "2010";
                }else if ("2009".equals(nTimeLimit)) {
                    workingSince = "2009";
                }else if ("2008".equals(nTimeLimit)) {
                    workingSince = "2008";
                }else if ("2007".equals(nTimeLimit)) {
                    workingSince = "2007";
                }else if ("2006".equals(nTimeLimit)) {
                    workingSince = "2006";
                }else if ("2005".equals(nTimeLimit)) {
                    workingSince = "2005";
                }else if ("2004".equals(nTimeLimit)) {
                    workingSince = "2004";
                }else if ("2003".equals(nTimeLimit)) {
                    workingSince = "2003";
                }else if ("2002".equals(nTimeLimit)) {
                    workingSince = "2002";
                }else if ("2001".equals(nTimeLimit)) {
                    workingSince = "2001";
                }else if ("2000".equals(nTimeLimit)) {
                    workingSince = "2000";
                }
                Message message = new Message();
                message.what = 3;
                message.obj = workingSince;
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
     * 新的参加工作时间
     */
    private void pickData() {
        View outerView1 = LayoutInflater.from(this).inflate(R.layout.dialog_date_select, null);
        //年滚轮
        final WheelView wv1 = (WheelView) outerView1.findViewById(R.id.wv1);
        //月滚轮
        final WheelView wv2 = (WheelView) outerView1.findViewById(R.id.wv2);
        //日滚轮
        final WheelView wv3 = (WheelView) outerView1.findViewById(R.id.wv3);
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        int year = ca.get(Calendar.YEAR);
        System.out.println("年 : " + year);
        int month = ca.get(Calendar.MONTH);
        System.out.println("第几月  ;" + month);
        int day = ca.get(Calendar.DAY_OF_MONTH);
        System.out.println("某月第几天 : " + day);
        String months;
        if (month < 10) {
            months = "0" + (month + 1);
        } else {
            months = month + 1 + "";
        }
        wv1.setItems(Common.yearBList(30), 0);
        wv2.setItems(Common.monthList(), month);
        wv3.setItems(Common.dayList(year + "", months), day - 1);
        wv1.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                String yearItem = wv1.getSelectedItem();
                String monthItem = wv2.getSelectedItem();
                yearItem = yearItem.substring(0, yearItem.length() - 1);
                monthItem = monthItem.substring(0, monthItem.length() - 1);
                wv2.setItems(Common.monthList(), 0);
                wv3.setItems(Common.dayList(yearItem, monthItem), 0);
            }
        });
        wv2.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index, String item) {
                String yearItem = wv1.getSelectedItem();
                String monthItem = wv2.getSelectedItem();
                yearItem = yearItem.substring(0, yearItem.length() - 1);
                monthItem = monthItem.substring(0, monthItem.length() - 1);
                wv3.setItems(Common.dayList(yearItem, monthItem), 0);
            }
        });
        wv3.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
            }
        });
        TextView tv_ok = (TextView) outerView1.findViewById(R.id.tv_ok);
        TextView tv_cancel = (TextView) outerView1.findViewById(R.id.tv_cancel);
        //点击确定
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                bottomDialog.dismiss();
                String mSelectDate = wv1.getSelectedItem();
                String mSelectHour = wv2.getSelectedItem();
                String mSelectMin = wv3.getSelectedItem();

                int year = ca.get(Calendar.YEAR);
                int month = ca.get(Calendar.MONTH) + 1;
                int day = ca.get(Calendar.DAY_OF_MONTH);

                //mSelectDate = mSelectDate.substring(0, mSelectDate.length() - 1);
               // mSelectHour = mSelectHour.substring(0, mSelectHour.length() - 1);
                //mSelectMin = mSelectMin.substring(0, mSelectMin.length() - 1);
                String age = mSelectDate+"/"+mSelectHour+"/"+mSelectMin;
                Message message = new Message();
                message.what = 3;
                message.obj = age;
                handler.sendMessage(message);
            }
        });
        //点击取消
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                bottomDialog.dismiss();
            }
        });
        //防止弹出两个窗口
        if (bottomDialog != null && bottomDialog.isShowing()) {
            return;
        }

        bottomDialog = new BottomDialog(this, R.style.ActionSheetDialogStyle);
        //将布局设置给Dialog
        bottomDialog.setContentView(outerView1);
        bottomDialog.show();//显示对话框
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
                            tvText4.setText(textTipsBean.getText().getText4());
                            tvText4_1.setText(textTipsBean.getText().getText4_1());
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
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getWork_type())){
                                tvType.setText(verifyStepInfoBean.getUSER().getWork_type());
                            }
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getBasic_monthly_income())) {
                                tvIncome.setText(verifyStepInfoBean.getUSER().getBasic_monthly_income());
                            }
                            if (!TextUtils.isEmpty(verifyStepInfoBean.getUSER().getBasic_total_employment())) {
                                tvWorkingSince.setText(verifyStepInfoBean.getUSER().getBasic_total_employment());
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
     * 向后台提交就业信息
     */
    private void sendInfo() {
        if (TextUtils.isEmpty(tvType.getText().toString().trim())){
            ToastUtils.showShort("Please check your Employment Type;");
            return;
        }
        if (TextUtils.isEmpty(tvIncome.getText().toString().trim())){
            ToastUtils.showShort("Please check your Income;");
            return;
        }
        if (TextUtils.isEmpty(tvWorkingSince.getText().toString().trim())){
            ToastUtils.showShort("Please check your Working since;");
            return;
        }
        OkGo.post(Api.SUBMIT_INFO)
                .headers("uid", SPUtils.getInstance().getInt("uid") + "")
                .headers("token", SPUtils.getInstance().getString("token"))
                .params("work_type", tvType.getText().toString().trim())
                .params("Income", tvIncome.getText().toString().trim())
                .params("Employment", tvWorkingSince.getText().toString().trim())
                .execute(new JsonCallBack() {
                    @Override
                    public void onResponse(String json) {
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
                .params("currentStep", "employment")
                .execute(new JsonCallBack() {
                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        StepInfoBean stepInfoBean = gson.fromJson(json, StepInfoBean.class);
                        if ("200".equals(stepInfoBean.getCode())) {
                            String nextStep = stepInfoBean.getData().getNextStep();
                            if ("BindCard".equals(nextStep)){
                               finish();
                            }else {
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

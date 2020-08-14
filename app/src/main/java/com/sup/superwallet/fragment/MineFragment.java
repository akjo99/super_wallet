package com.sup.superwallet.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.sup.superwallet.activity.AuthenticationActivity;
import com.sup.superwallet.activity.BankActivity;
import com.sup.superwallet.activity.BaseInforActivity;
import com.sup.superwallet.activity.CameraStepActivity;
import com.sup.superwallet.activity.IDAuthActivity;
import com.sup.superwallet.activity.MainActivity;
import com.sup.superwallet.activity.NewBaseInforActivity;
import com.sup.superwallet.activity.NewPayActivity;
import com.sup.superwallet.activity.WalletActivity;
import com.sup.superwallet.bean.StepInfoBean;
import com.google.gson.Gson;
import com.sup.superwallet.bean.WalletBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;
import com.sup.superwallet.R;
import com.sup.superwallet.activity.InformationActivity;
import com.sup.superwallet.activity.LoginActivity;
import com.sup.superwallet.activity.PayActivity;
import com.sup.superwallet.activity.QuitActivity;
import com.sup.superwallet.activity.WebActivity;
import com.sup.superwallet.adapter.MineAdapter;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseFragment;
import com.sup.superwallet.bean.UserBean;
import com.sup.superwallet.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_my_quota)
    ImageView tvMyQuota;
    @BindView(R.id.rc_mine)
    RecyclerView rcMine;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;
    private MineAdapter mineAdapter;
    private List<UserBean.DataBean.ServiceListBean> serviceListBeans = new ArrayList<>();


    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        rcMine.setLayoutManager(new LinearLayoutManager(getContext()));
        mineAdapter = new MineAdapter(serviceListBeans);
        rcMine.setAdapter(mineAdapter);
        getSwitch();
        getSelfData();
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_person_information, R.id.ll_edit, R.id.tv_my_quota,R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_person_information:
                Intent intent = new Intent(getContext(), InformationActivity.class);
                startActivity(intent);
                break;
//            case R.id.ll_edit:
//                //先询问是否退出？
////                //将登录状态设为未登录
////                SPUtils.getInstance().put("isLogin", false);
////                SPUtils.getInstance().put("is_vip", false);
////                Intent intent2 = new Intent(getContext(), LoginActivity.class);
////                startActivity(intent2);
//                break;

            case R.id.tv_my_quota:
                //暂时是跳到拍照页面，以后可能会有验证
//                Intent intent1 = new Intent(getContext(), PayActivity.class);
//                startActivity(intent1);
                step();
                break;

            case R.id.iv_setting:
                //暂时调到支付页面f
               creatDialog2();
                break;
        }
    }

    private void getSelfData() {
        OkGo.post(Api.USER)
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
                        UserBean userBean = gson.fromJson(json, UserBean.class);
                        if ("200".equals(userBean.getCode())) {
                            tvNumber.setText("Hey!\t\t"+userBean.getData().getUser_info().getMobile());
                            mineAdapter.setNewData(userBean.getData().getService_list());
                            mineAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    if (2 == userBean.getData().getService_list().get(position).getType()) {
                                        //显示一个弹窗
                                        creatDialog1(userBean.getData().getService_list().get(position).getContent());

                                    } else if ("Feedback".equals(userBean.getData().getService_list().get(position).getService_name())) {
                                        Intent intent = new Intent(getContext(), WebActivity.class);
                                        intent.putExtra("url", userBean.getData().getService_list().get(position).getContent() +
                                                "?uid=" + SPUtils.getInstance().getInt("uid") + "&token=" + SPUtils.getInstance().getString("token"));
                                        intent.putExtra("title", userBean.getData().getService_list().get(position).getService_name());
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(getContext(), WebActivity.class);
                                        intent.putExtra("url", userBean.getData().getService_list().get(position).getContent());
                                        intent.putExtra("title", userBean.getData().getService_list().get(position).getService_name());
                                        startActivity(intent);
                                    }

                                }
                            });
                        } else {
                            ToastUtils.showShort(userBean.getMsg());
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

    /**
     * 点击弹出email的弹窗
     */

    private void creatDialog1(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_email, null, false);
        ViewHolder viewHolder1 = new ViewHolder(v);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();
        viewHolder1.tvEmail.setText("" + text);
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

    private void creatDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_quit_confirm, null, false);
        ViewHolder2 viewHolder2 = new ViewHolder2(v);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();

        viewHolder2.llQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将登录状态设为未登录
                SPUtils.getInstance().put("isLogin", false);
                SPUtils.getInstance().put("is_vip", false);
                //uid与token清空
                SPUtils.getInstance().put("token", "");
                SPUtils.getInstance().put("uid", 0);
                Intent intent2 = new Intent(getContext(), LoginActivity.class);
                startActivity(intent2);
            }
        });

        viewHolder2.llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_quit)
        LinearLayout llQuit;
        @BindView(R.id.ll_cancel)
        LinearLayout llCancel;
        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            getSelfData();
//        }
//    }

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
                        tvMyQuota.setEnabled(false);
                        //multipleStatusView.showLoading();
                    }

                    @Override
                    public void onResponse(String json) {
                       // multipleStatusView.showContent();
                        tvMyQuota.setEnabled(true);
                        Gson gson = new Gson();
                        StepInfoBean stepInfoBean = gson.fromJson(json, StepInfoBean.class);
                        if ("200".equals(stepInfoBean.getCode())) {
                            String nextStep = stepInfoBean.getData().getFirstStep();
                            Log.d("BankActivity", "nextStep为：" + nextStep);
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
                            }else if ("Wallet".equals(nextStep)){
                                Intent intent = new Intent(getContext(),WalletActivity.class);
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
     * 后台控制的验证
     */
    public void getSwitch() {
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
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        StepInfoBean stepInfoBean = gson.fromJson(json, StepInfoBean.class);
                        if ("200".equals(stepInfoBean.getCode())) {
                            if (1 == stepInfoBean.getData().getPaySwitch()) {
                                tvMyQuota.setVisibility(View.VISIBLE);
                            } else {

                                tvMyQuota.setVisibility(View.INVISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

}

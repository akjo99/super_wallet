package com.sup.superwallet.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.sup.superwallet.R;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.utils.JsonCallBack;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraStepActivity extends BaseActivity {

    @BindView(R.id.tv_sumbit)
    TextView tvSubmit;
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.tv_text2_4)
    TextView tvText2_4;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;

    private int isClick = 0;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 999) {
                step();
            }
        }
    };


    //安卓调用相机，实现拍照的功能
    @Override
    protected int getLayout() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getTips();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
                if (ActivityCompat.checkSelfPermission(CameraStepActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 系统常量， 启动相机的关键
                    startActivityForResult(openCameraIntent, 200); // 参数常量为自定义的request code, 在取返回结果时有用
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
                }
            }
        });

//        tvSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isClick ==1) {
//                    step(); //进程控制
//                }else {
//                    ToastUtils.showShort("Please pass the face verification!");
//                }
//            }
//        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 申请系统权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasAllGranted = true;
        int pos = 0;
        //判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                pos = i;
                break;
            }
        }
        if (hasAllGranted) {
            //执行调用摄像头的操作
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 系统常量， 启动相机的关键
            startActivityForResult(openCameraIntent, 200); // 参数常量为自定义的request code, 在取返回结果时有用
        } else {
            //继续申请系统权限
            return;
        }
    }

    /**
     * 假装调取人脸识别，然后将拍的照片展示在控件上
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 200:
                // 此处写“如何获取图片”...
                if (intent == null) return;
                if (null == intent.getExtras()) return;
                Bitmap bm = (Bitmap) intent.getExtras().get("data");
                isClick = 1;
                creatDialog();
                //直接进行step操作
                Message message = new Message();
                message.what = 999;
                handler.sendMessageDelayed(message,3000);
                break;
        }

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
                            tvText2.setText(textTipsBean.getText().getText2().replace("\\n","\n"));
                            tvText2_4.setText(textTipsBean.getText().getText2_1().replace("\\n","\n"));
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
        String stringValue = "FaceAuth";
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
                            if ("FaceAuth".equals(nextStep)){
                                Intent intent = new Intent(CameraStepActivity.this, CameraStepActivity.class);
                                startActivity(intent);
                            }else if ("IDAuth".equals(nextStep)){
                                Intent intent = new Intent(CameraStepActivity.this,IDAuthActivity.class);
                                startActivity(intent);
                                finish();
                            }else if("BasicInfo".equals(nextStep)){
                                Intent intent = new Intent(CameraStepActivity.this,NewBaseInforActivity.class);
                                startActivity(intent);
                                finish();
                            }else if ("MountVerify".equals(nextStep)){
                                Intent intent = new Intent(CameraStepActivity.this,AuthenticationActivity.class);
                                startActivity(intent);
                                finish();
                            }else if ("BindCard".equals(nextStep)){
                                Intent intent = new Intent(CameraStepActivity.this,BankActivity.class);
                                startActivity(intent);
                                finish();
                            }else if ("PaymentSelect".equals(nextStep)){
                                Intent intent = new Intent(CameraStepActivity.this,NewPayActivity.class);
                                startActivity(intent);
                                finish();
                            }else if ("Finish".equals(nextStep)){
                                Intent intent = new Intent(CameraStepActivity.this,MainActivity.class);
                                startActivity(intent);
                                SPUtils.getInstance().put("is_vip", true);
                                finish();
                            }else if ("Wallet".equals(nextStep)){
                                Intent intent = new Intent(CameraStepActivity.this,WalletActivity.class);
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
     * 支付失败所对应的弹窗
     */

    private void creatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraStepActivity.this);
        View v = LayoutInflater.from(CameraStepActivity.this).inflate(R.layout.dialog_loading_cam, null, false);
        ViewHolder viewHolder = new ViewHolder(v);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}

package com.sup.superwallet.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.sup.superwallet.R;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.bean.StepInfoBean;
import com.sup.superwallet.bean.TextTipsBean;
import com.sup.superwallet.utils.BottomDialog;
import com.sup.superwallet.utils.JsonCallBack;
import com.sup.superwallet.utils.WheelView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IDAuthActivity extends BaseActivity {
    @BindView(R.id.tv_text3)
    TextView tvText3;
    @BindView(R.id.tv_text3_1)
    TextView tvText3_1;
    @BindView(R.id.tv_text3_2)
    TextView tvText3_2;
    @BindView(R.id.tv_text3_3)
    TextView tvText3_3;
    @BindView(R.id.iv_image_test)
    ImageView imageTest;
    @BindView(R.id.iv_image_test_2)
    ImageView imageTest2;
    @BindView(R.id.tv_sumbit)
    TextView tvSubmit;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    private int sel = 0;
    private int remSel = 0;
    private BottomDialog bottomDialog;
    private static final int select = 0; //选择从相册选择还是相机选择

    public static final int SELECT_PHOTO_1 = 3;

    private int isClickFirst = 0;
    private int isClickSecond = 0;


    @Override
    protected int getLayout() {
        return R.layout.activity_idauth;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getTips();
    }

    @Override
    protected void initListener() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> sexS = new ArrayList<>();
                sexS.add("Camera");
                sexS.add("Photo album");
                limits(sexS, 1);


                //改成可拍照也可上传
//                if (ActivityCompat.checkSelfPermission(IDAuthActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    sel = 1;
//                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 系统常量， 启动相机的关键
//                    startActivityForResult(openCameraIntent, 200); // 参数常量为自定义的request code, 在取返回结果时有用
//                } else {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
//                }
//                if (ActivityCompat.checkSelfPermission(IDAuthActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    sel = 1;
//                    Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
//                    intent.setType("image/*");
//                    startActivityForResult(intent,100);
//                } else {
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
//                }
            }
        });

        imageTest2.setOnClickListener(new View.OnClickListener() {
            //            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(IDAuthActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    sel = 2;
//                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 系统常量， 启动相机的关键
//                    startActivityForResult(openCameraIntent, 200); // 参数常量为自定义的request code, 在取返回结果时有用
//                } else {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
//                }
                List<String> sexS = new ArrayList<>();
                sexS.add("Camera");
                sexS.add("Photo album");
                limits(sexS, 2);
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((isClickFirst == 1) && (isClickSecond == 1)) {
                    step();
                } else {
                    ToastUtils.showShort("Please pass the identification！");
                }
            }
        });

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
            if (remSel == 1) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 系统常量， 启动相机的关键
                startActivityForResult(openCameraIntent, 200); // 参数常量为自定义的request code, 在取返回结果时有用
            } else if (remSel == 2) {
                Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
                intent.setType("image/*");
                startActivityForResult(intent, 100);

            }
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
            //相机拍的
            case 200:
                // 此处写“如何获取图片”...
                if (intent == null) return;
                if (null == intent.getExtras()) return;
                Bitmap bm = (Bitmap) intent.getExtras().get("data");
                if (sel == 1) {
                    imageTest.setImageBitmap(bm);
                    imageTest.setScaleType(ImageView.ScaleType.FIT_XY);
                    isClickFirst = 1;
                    tvText3_1.setVisibility(View.GONE);
                } else if (sel == 2) {
                    imageTest2.setImageBitmap(bm);
                    imageTest2.setScaleType(ImageView.ScaleType.FIT_XY);
                    isClickSecond = 1;
                    tvText3_2.setVisibility(View.GONE);
                }
                break;
            //相册取的
            case 100:
                if (intent == null) return;
                if (null == intent.getExtras()) return;
                Uri uri01 = intent.getData();
                Bitmap bm1 = null;
                try {
                    bm1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri01));
                    if (sel == 1) {
                        imageTest.setImageBitmap(bm1);
                        imageTest.setScaleType(ImageView.ScaleType.FIT_XY);
                        isClickFirst = 1;
                        tvText3_1.setVisibility(View.GONE);
                    } else if (sel == 2) {
                        Log.d("呵呵呵", "这里执行啦");
                        imageTest2.setImageBitmap(bm1);
                        imageTest2.setScaleType(ImageView.ScaleType.FIT_XY);
                        isClickSecond = 1;
                        tvText3_2.setVisibility(View.GONE);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    /**
     * 选择直接拍照还是从相册选择
     */
    private void limits(List<String> limits, int code) {
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
                if ("Camera".equals(nTimeLimit)) {
                    sel = code;
                    //从相机选择
                    if (ActivityCompat.checkSelfPermission(IDAuthActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 系统常量， 启动相机的关键
                        startActivityForResult(openCameraIntent, 200); // 参数常量为自定义的request code, 在取返回结果时有用
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            remSel = 1;
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
                        }
                    }

                } else if ("Photo album".equals(nTimeLimit)) {

                    sel = code;
                    //从相册选择
                    if (ActivityCompat.checkSelfPermission(IDAuthActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
                        intent.setType("image/*");
                        startActivityForResult(intent, 100);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            remSel = 2;
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }
                    }

                }
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
                            tvText3.setText(textTipsBean.getText().getText3().replace("\\n","\n"));
//                            tvText3_1.setText(textTipsBean.getText().getText3_1().replace("\\n","\n"));
//                            tvText3_2.setText(textTipsBean.getText().getText3_2().replace("\\n","\n"));
                            tvText3_3.setText(textTipsBean.getText().getText3_1().replace("\\n","\n"));
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
        String stringValue = "IDAuth";
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
                                Intent intent = new Intent(IDAuthActivity.this, CameraStepActivity.class);
                                startActivity(intent);
                            } else if ("IDAuth".equals(nextStep)) {
                                Intent intent = new Intent(IDAuthActivity.this, IDAuthActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("BasicInfo".equals(nextStep)) {
                                Intent intent = new Intent(IDAuthActivity.this, NewBaseInforActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("MountVerify".equals(nextStep)) {
                                Intent intent = new Intent(IDAuthActivity.this, AuthenticationActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("BindCard".equals(nextStep)) {
                                Intent intent = new Intent(IDAuthActivity.this, BankActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("PaymentSelect".equals(nextStep)) {
                                Intent intent = new Intent(IDAuthActivity.this, NewPayActivity.class);
                                startActivity(intent);
                                finish();
                            } else if ("Finish".equals(nextStep)) {
                                Intent intent = new Intent(IDAuthActivity.this, MainActivity.class);
                                startActivity(intent);
                                SPUtils.getInstance().put("is_vip", true);
                                finish();
                            }else if ("Wallet".equals(nextStep)){
                                Intent intent = new Intent(IDAuthActivity.this,WalletActivity.class);
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
     * 从相册选择
     */
//    private void select_photo() {
//        if (ContextCompat.checkSelfPermission(IDAuthActivity.this,
//                "android.permission.WRITE_EXTERNAL_STORAGE")
//                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(IDAuthActivity.this,
//                "android.permission.READ_EXTERNAL_STORAGE")
//                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(IDAuthActivity.this,
//                "android.permission.CAMERA")
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(IDAuthActivity.this,
//                    new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permissin.WRITE_SETTINGS"},
//                    SELECT_PHOTO_1);
//        } else {
//            openAlbum();
//        }
//    }

//    private void openAlbum() {
//        PictureSelectionModel pictureSelectionModel = PictureSelector.create(IDAuthActivity.this)
//                .openGallery(PictureMimeType.ofImage());
//        pictureSelectionModel.maxSelectNum(1);//最大图片选择数量
//        pictureSelectionModel.imageSpanCount(3);//每行显示个数
//        pictureSelectionModel.compress(true);//是否压缩
//        pictureSelectionModel.enableCrop(true);//是否剪切
//        pictureSelectionModel.showCropGrid(false);//是否显示裁剪矩形网格
//        pictureSelectionModel.showCropFrame(false);//是否显示剪切矩形网格
//        // pictureSelectionModel.cropWH(150, 150);
//        pictureSelectionModel
//                .circleDimmedLayer(true)
//                .withAspectRatio(1, 1)// 裁剪比例
//                .isCamera(false).
//                imageFormat(PictureMimeType.PNG).
//                minimumCompressSize(500).isGif(false).
//                forResult(PictureConfig.CHOOSE_REQUEST);
//
//    }
}

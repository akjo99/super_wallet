package com.sup.superwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.sup.superwallet.R;
import com.sup.superwallet.base.BaseActivity;

import butterknife.BindView;

public class QuitActivity extends BaseActivity {
    @BindView(R.id.tv_sumbit)
    TextView quit;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @Override
    protected int getLayout() {
        return R.layout.activity_quit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将登录状态设为未登录
                SPUtils.getInstance().put("isLogin", false);
                SPUtils.getInstance().put("is_vip", false);
                Intent intent = new Intent(QuitActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
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

}

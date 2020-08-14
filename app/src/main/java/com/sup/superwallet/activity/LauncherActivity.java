package com.sup.superwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.sup.superwallet.R;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.utils.ImgLoader;

import butterknife.BindView;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class LauncherActivity extends BaseActivity {

    @BindView(R.id.cover)
    ImageView mCover;
    @Override
    protected int getLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        ImgLoader.display(this, R.drawable.screen, mCover);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.forward(LauncherActivity.this);
//                LoginActivity.forward(LauncherActivity.this);
//                HomeActivity.forward(LauncherActivity.this);
                finish();
            }
        }, 3000);

    }

    @Override
    public void onStart() {
        super.onStart();
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // if activity is in foreground (or in backstack but partially visible) launching the same
        // activity will skip onStart, handle this case with reInitSession
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit();
    }

    private Branch.BranchReferralInitListener branchReferralInitListener = new Branch.BranchReferralInitListener() {
        @Override
        public void onInitFinished(@Nullable org.json.JSONObject referringParams, @Nullable BranchError error) {

        }

    };
}

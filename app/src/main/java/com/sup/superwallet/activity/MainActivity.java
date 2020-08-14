package com.sup.superwallet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.sup.superwallet.R;
import com.sup.superwallet.base.BaseActivity;
import com.sup.superwallet.fragment.HomeFragment;
import com.sup.superwallet.fragment.MineFragment;
import com.sup.superwallet.fragment.ProductFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.main_home)
    RadioButton rbMainHome;
    @BindView(R.id.main_mine)
    RadioButton rbMainMine;
    FragmentManager fragmentManager;
    Fragment mHomeFragment, mMineFragment;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayout() {

        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        boolean isVip = SPUtils.getInstance().getBoolean("is_vip", false);
        if (isVip) {
            initNewFragment();
        } else {
            initFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        rbMainHome.setChecked(true);
    }

    public static void forward(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void initListener() {

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.main_home:
                        onTab(0);
                        break;
                    case R.id.main_mine:
                        boolean isLogin = SPUtils.getInstance().getBoolean("isLogin", false);
                        if (!isLogin) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            onTab(1);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    //贷超页面的首页

    private void initNewFragment() {
        rgMain.check(R.id.main_home); //默认选中首页
        mHomeFragment = new ProductFragment();
        if (mMineFragment == null) {
            mMineFragment = new MineFragment();
        }
        fragments.clear();
        fragments.add(mHomeFragment);
        fragments.add(mMineFragment);

        //将fragment文件放入FrameLayout中
        fragmentManager.beginTransaction()
                .add(R.id.fl_main, fragments.get(0))
                .add(R.id.fl_main, fragments.get(1))
                .hide(fragments.get(1))
                .commit();
    }

    //默认页面的首页

    private void initFragment() {
        rgMain.check(R.id.main_home); //默认选中首页
        mHomeFragment = new HomeFragment();
        if (mMineFragment == null) {
            mMineFragment = new MineFragment();
        }
        fragments.clear();
        fragments.add(mHomeFragment);
        fragments.add(mMineFragment);

        //将fragment文件放入FrameLayout中
        fragmentManager.beginTransaction()
                .add(R.id.fl_main, fragments.get(0))
                .add(R.id.fl_main, fragments.get(1))
                .hide(fragments.get(1))
                .commit();
    }

    /**
     * 切换tab
     */
    private void onTab(int position) {

        fragmentManager.beginTransaction()
                .show(fragments.get(position))
                .hide(fragments.get((position + 1) % 2))
                .commit();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//            return true;
//        return super.onKeyDown(keyCode, event);
//    }

}
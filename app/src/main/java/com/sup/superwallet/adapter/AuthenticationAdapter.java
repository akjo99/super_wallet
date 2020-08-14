package com.sup.superwallet.adapter;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sup.superwallet.R;
import com.sup.superwallet.bean.AuthBean;

import java.util.List;

public class AuthenticationAdapter extends BaseQuickAdapter<AuthBean.DataArrBean,BaseViewHolder> {

    public AuthenticationAdapter( @Nullable List<AuthBean.DataArrBean> data) {
        super(R.layout.item_new_baseinfo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthBean.DataArrBean item) {
        helper.setText(R.id.tv_name1,item.getTitle())
        .setGone(R.id.tv_email,"0".equals(item.getInputType()))
        .setGone(R.id.ed_email,!"0".equals(item.getInputType()))
        .setGone(R.id.iv_arrow_2,"0".equals(item.getInputType()));
    }
}

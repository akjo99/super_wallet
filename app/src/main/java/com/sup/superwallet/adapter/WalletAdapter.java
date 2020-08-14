package com.sup.superwallet.adapter;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sup.superwallet.R;
import com.sup.superwallet.bean.WalletBean;

import java.util.List;

public class WalletAdapter extends BaseQuickAdapter<WalletBean.OrderBean, BaseViewHolder> {
    public WalletAdapter( @Nullable List<WalletBean.OrderBean> data) {
        super(R.layout.item_wallet, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, WalletBean.OrderBean item) {
        int length =item.getCreate_time().length();
        helper.setText(R.id.tv_amount,item.getAmount()+"")
        .setText(R.id.tv_time,item.getCreate_time().substring(5,length))
        .setText(R.id.tv_text6_2, SPUtils.getInstance().getString("text7_4"));
    }
}

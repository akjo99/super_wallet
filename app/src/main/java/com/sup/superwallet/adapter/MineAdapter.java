package com.sup.superwallet.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sup.superwallet.R;
import com.sup.superwallet.bean.UserBean;

import java.util.List;

public class MineAdapter extends BaseQuickAdapter<UserBean.DataBean.ServiceListBean,BaseViewHolder> {

    public MineAdapter(@Nullable List<UserBean.DataBean.ServiceListBean> data) {
        super(R.layout.item_mine_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean.DataBean.ServiceListBean item) {
        Glide.with(mContext).load(item.getService_icon()).into((ImageView) helper.getView(R.id.iv_img1));
        helper.setText(R.id.tv_name1,item.getService_name());

    }
}

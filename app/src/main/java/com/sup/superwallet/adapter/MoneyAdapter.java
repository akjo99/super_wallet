package com.sup.superwallet.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sup.superwallet.R;
import com.sup.superwallet.bean.PayBean;
import com.sup.superwallet.bean.PayDataBean;

import java.util.List;

public class MoneyAdapter extends BaseQuickAdapter<PayDataBean.LoanInfoBean, BaseViewHolder> {
    public MoneyAdapter(@Nullable List<PayDataBean.LoanInfoBean> data) {
        super(R.layout.item_pay_money, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, PayDataBean.LoanInfoBean item) {
        helper.setText(R.id.tv_amount, item.getLoan_amount())
                .setText(R.id.tv_interest, "Interest:\t" + item.getShow_amount()+"\t\t\t"+"Cycle:\t" + item.getLoan_time());
    }
}

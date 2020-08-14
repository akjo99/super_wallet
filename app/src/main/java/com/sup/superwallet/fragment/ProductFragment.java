package com.sup.superwallet.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.Request;
import com.sup.superwallet.R;
import com.sup.superwallet.adapter.ProductAdapter;
import com.sup.superwallet.api.Api;
import com.sup.superwallet.base.BaseFragment;
import com.sup.superwallet.bean.NewProductBean;
import com.sup.superwallet.utils.JsonCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductFragment extends BaseFragment {

    @BindView(R.id.rc_product)
    RecyclerView rcProduct;
    @BindView(R.id.msv_status)
    MultipleStatusView multipleStatusView;
    ProductAdapter productAdapter;
    // private List<ProductDataBean.DataBean.ProductClassBean> productClassBeans = new ArrayList<>();
    private List<NewProductBean.DataBean.ProductBean> productBeans = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initView(View view) {

        getProductList();
        rcProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(productBeans);
        rcProduct.setAdapter(productAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void getProductList() {
        OkGo.post(Api.LOAN_URL)
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
                        NewProductBean newProductBean = gson.fromJson(json, NewProductBean.class);
                        productAdapter.setNewData(newProductBean.getData().getProduct());
                        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                Intent intent = new Intent(getContext(), WebActivity.class);
//                                intent.putExtra("url", newProductBean.getData().getProduct().get(position).getGoods_url());
//                                intent.putExtra("title", newProductBean.getData().getProduct().get(position).getGoods_name());
//                                startActivity(intent);
                                //跳转战外
                                Intent intent2 = new Intent();
                                intent2.setData(Uri.parse(newProductBean.getData().getProduct().get(position).getGoods_url()));
                                intent2.setAction(Intent.ACTION_VIEW);
                                startActivity(intent2);

                            }
                        });
                    }

                    @Override
                    public void onError(String json) {
                    }
                });
    }
}
